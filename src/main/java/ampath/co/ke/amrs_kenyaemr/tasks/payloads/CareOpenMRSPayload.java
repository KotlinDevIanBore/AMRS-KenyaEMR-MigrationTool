package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

import ampath.co.ke.amrs_kenyaemr.methods.AMRSTranslater;
import ampath.co.ke.amrs_kenyaemr.models.*;
import ampath.co.ke.amrs_kenyaemr.service.*;
import ampath.co.ke.amrs_kenyaemr.tasks.Mappers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CareOpenMRSPayload {
    public static void programs(AMRSProgramService amrsProgramService, String url, String auth) throws JSONException, IOException {
        //List<AMRSPrograms> amrsProgramsList = amrsProgramService.findByParentLocationUuid(parentUUID);
        List<AMRSPrograms> amrsProgramsList = amrsProgramService.findByResponseCodeIsNull();
        if (!amrsProgramsList.isEmpty()) {
            JSONObject jsonProgram = new JSONObject();
            for (int x = 0; x < amrsProgramsList.size(); x++) {

                String programms = Mappers.programs(String.valueOf(amrsProgramsList.get(x).getProgramUUID()));
                System.out.println("Program UUID is here " + programms + " amrs UUID " + amrsProgramsList.get(x).getProgramUUID());
                int pid = amrsProgramsList.get(x).getProgramID();
                AMRSPrograms ap = amrsProgramsList.get(x);

                if (!programms.equals("") || ap.getPatientKenyaemrUuid() != null) {

                    jsonProgram.put("patient", ap.getPatientKenyaemrUuid());
                    jsonProgram.put("program", ap.getKenyaemrProgramUuid());
                    jsonProgram.put("dateEnrolled", ap.getDateEnrolled());
                    //jsonProgram.put("location", ap.getDateEnrolled());
                    if (pid == 1 || pid == 3 || pid == 9 || pid == 20) {

                    } else {
                        jsonProgram.put("dateCompleted", ap.getDateCompleted());
                    }
                    System.out.println("Payload for Programs is here " + jsonProgram.toString());
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");
                    okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonProgram.toString());
                    //RequestBody body = RequestBody.create(mediaType, jsonEncounter.toString());
                    Request request = new Request.Builder()
                            .url(url + "programenrollment")
                            .method("POST", body)
                            .addHeader("Authorization", "Basic " + auth)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseBody = response.body().string(); // Get the response as a string
                    System.out.println("Response ndo hii " + responseBody + " More message " + response.message());

                    // String resBody = response.request().toString();
                    int rescode = response.code();
                    ap.setResponseCode(String.valueOf(rescode));
                    System.out.println("Imefika Hapa na data " + rescode);
                } else {
                    ap.setResponseCode(String.valueOf(400));
                }
                amrsProgramService.save(ap);
            }

        }
    }

    public static void triage( String KenyaEMRLocationUuid,AMRSTriageService amrsTriageService, AMRSPatientServices amrsPatientServices, AMRSEncounterService amrsEncounterService,AMRSVisitService amrsVisitService, String url, String auth) throws JSONException, IOException {

        List<AMRSTriage> amrsTriages = amrsTriageService.findByResponseCodeIsNull();
        if (!amrsTriages.isEmpty()) {
            // Use a Set to store unique encounter IDs
            Set<String> visistIdSet = new HashSet<>();
            List<String> distinctVisitIds = new ArrayList<>();
            // Loop through the list
            for (AMRSTriage triage : amrsTriages) {
                if (triage.getResponseCode() == null) {
                    String visitId = triage.getVisitId();
                    // Add to the result list only if it hasn't been added already
                    if (visistIdSet.add(visitId)) {
                        distinctVisitIds.add(visitId);
                    }
                }
            }

            for (String visitId : distinctVisitIds) {
                System.out.println("VisitId ID for Vitals " + visitId);
                List<AMRSTriage> amrsTriageEncounters = amrsTriageService.findByVisitId(visitId);
                //AMRSTriage at = amrsTriageEncounters.get(0);
                JSONArray jsonObservations = new JSONArray();
                String patientuuid = "";
               // List<AMRSPatients> amrsPatients = amrsPatientServices.getByPatientID(amrsTriageEncounters.get(0).getPatientId());
               // patientuuid = amrsPatients.get(0).getKenyaemrpatientUUID();
                for (int x = 0; x < amrsTriageEncounters.size(); x++) {

                    JSONObject jsonObservation = new JSONObject();
                    String value = amrsTriageEncounters.get(x).getValue();
                    // jsonObservation.put("person", amrsTriageEncounters.get(x).getKenyaemrPatientUuid());///String.valueOf(conceptsetId));
                    // jsonObservation.put("concept", amrsTriageEncounters.get(x).getKenyaemConceptId());///String.valueOf(conceptsetId));
                  /*  if (isDecimal(value)) {
                        jsonObservation.put("value", Double.parseDouble(amrsTriageEncounters.get(x).getValue()));

                    } else {
                        jsonObservation.put("value", Double.parseDouble(amrsTriageEncounters.get(x).getValue()));
                    }*/
                    jsonObservation.put("person", amrsTriageEncounters.get(x).getKenyaemrPatientUuid());///String.valueOf(conceptsetId));
                    jsonObservation.put("concept", amrsTriageEncounters.get(x).getKenyaemConceptId());///String.valueOf(conceptsetId));
                    jsonObservation.put("obsDatetime", amrsTriageEncounters.get(x).getObsDateTime());///String.valueOf(conceptsetId));
                    jsonObservation.put("value", amrsTriageEncounters.get(x).getValue());
                    jsonObservations.put(jsonObservation);
                    patientuuid = amrsTriageEncounters.get(x).getKenyaemrPatientUuid();
                }

               // System.out.println("Payload for is here " + jsonObservations.toString());

                // List<AMRSEncounters> amrsEncounters = amrsEncounterService.findByEncounterId(encounterId);
                // if (amrsEncounters.size() > 0) {

                if (amrsTriageEncounters.get(0).getVisitId()==null) {

                } else {
                    List<AMRSVisits> amrsVisits = amrsVisitService.findByVisitID(amrsTriageEncounters.get(0).getVisitId());

                    if (!amrsVisits.isEmpty()) {
                        JSONObject jsonEncounter = new JSONObject();
                        jsonEncounter.put("form", "37f6bd8d-586a-4169-95fa-5781f987fe62");
                        jsonEncounter.put("patient", patientuuid);
                        jsonEncounter.put("encounterDatetime", amrsTriageEncounters.get(0).getEncounterDateTime());
                        jsonEncounter.put("encounterType", "d1059fb9-a079-4feb-a749-eedd709ae542");
                        jsonEncounter.put("location", KenyaEMRLocationUuid);
                        jsonEncounter.put("visit", amrsVisits.get(0).getKenyaemrVisitUuid());
                        jsonEncounter.put("obs", jsonObservations);
                        System.out.println("Payload for is here " + jsonEncounter.toString());
                        System.out.println("URL is here " + url + "encounter/" + amrsTriageEncounters.get(0).getKenyaemrEncounterUuid());

                        System.out.println("Payload for is here " + jsonEncounter.toString());

                        OkHttpClient client = new OkHttpClient();
                        MediaType mediaType = MediaType.parse("application/json");
                        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());
                        //RequestBody body = RequestBody.create(mediaType, jsonEncounter.toString());
                        Request request = new Request.Builder()
                                // .url(url + "encounter/" + amrsEncounters.get(0).getKenyaemrEncounterUuid())
                                .url(url + "encounter")
                                .method("POST", body)
                                .addHeader("Authorization", "Basic " + auth)
                                .addHeader("Content-Type", "application/json")
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseBody = response.body().string(); // Get the response as a string
                        System.out.println("Response ndo hii " + responseBody + " More message " + response.message());

                        String resBody = response.request().toString();
                        int rescode = response.code();
                        System.out.println("Response Code Hapa " + rescode);

                        if (rescode == 201) {
                            for (int x = 0; x < amrsTriageEncounters.size(); x++) {
                                AMRSTriage at = amrsTriageEncounters.get(x);
                                at.setResponseCode(String.valueOf(rescode));
                                at.setResponseCode("201");
                                at.setKenyaemrEncounterUuid(amrsTriageEncounters.get(0).getKenyaemrEncounterUuid());
                                System.out.println("Imefika Hapa na data " + rescode);
                                amrsTriageService.save(at);
                            }
                        }
                    }
                }
            }
        }



           /* JSONArray jsonObservations = new JSONArray();
            for (int x = 0; x < amrsTriages.size(); x++) {
                AMRSTriage at = amrsTriages.get(x);
                List<AMRSPatients> amrsPatients = amrsPatientServices.getByPatientID(at.getPatientId());
                List<AMRSEncounters> amrsEncounters = amrsEncounterService.findByEncounterId(at.getEncounterId());
                System.out.println("Patient ID " + amrsPatients.get(0).getPersonId() +" Encounter Id "+ at.getEncounterId());
                //Height
                //wight,tempdbp,sbp,bmi,pulse,rr,sop,
                JSONObject jsonEncounter = new JSONObject();
                jsonEncounter.put("form",at.getKenyaemrFormUuid());

              /*  JSONObject jsonObservationBMI = new JSONObject();
                jsonObservationBMI.put("person",amrsPatients.get(0).getKenyaemrpatientUUID());
                jsonObservationBMI.put("concept",conceptuuid);///String.valueOf(conceptsetId));
                jsonObservationBMI.put("value", at.getBmi());
                */
               /* JSONObject jsonEncounter = new JSONObject();
                jsonEncounter.put("form",at.getKenyaemrFormUuid());

                JSONObject jsonObservationTEMP = new JSONObject();
                jsonObservationTEMP.put("concept","5088AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");///String.valueOf(conceptsetId));
                jsonObservationTEMP.put("value", at.getTemperature());
                jsonObservations.put(jsonObservationTEMP);

                JSONObject jsonObservationW = new JSONObject();
               // jsonObservationW.put("person",amrsPatients.get(0).getKenyaemrpatientUUID());
                jsonObservationW.put("concept","5089AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");///String.valueOf(conceptsetId));
                jsonObservationW.put("value", at.getWeight());
                jsonObservations.put(jsonObservationW);

                JSONObject jsonObservationH = new JSONObject();
                jsonObservationH.put("concept","5090AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");///String.valueOf(conceptsetId));
                jsonObservationH.put("value", at.getHeight());
                jsonObservations.put(jsonObservationH);
                */

               /* jsonEncounter.put("obs",jsonObservations);


                System.out.println("Payload for is here " + jsonEncounter.toString());
                System.out.println("URL is here " + url + "encounter/"+amrsEncounters.get(0).getKenyaemrEncounterUuid());
                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());
                //RequestBody body = RequestBody.create(mediaType, jsonEncounter.toString());
                Request request = new Request.Builder()
                        .url(url + "encounter/"+amrsEncounters.get(0).getKenyaemrEncounterUuid())
                        .method("POST", body)
                        .addHeader("Authorization", "Basic " + auth)
                        .addHeader("Content-Type", "application/json")
                        .build();
                */
            // Response response = client.newCall(request).execute();
            // String responseBody = response.body().string(); // Get the response as a string
            // System.out.println("Response ndo hii " + responseBody + " More message " + response.message());

            // String resBody = response.request().toString();
            //  int rescode = response.code();
            // at.setResponseCode(String.valueOf(rescode));
            //  System.out.println("Imefika Hapa na data " + rescode);

            // amrsTriageService.save(at);



           /*     if(amrsEncounters.size()>0) {
                    System.out.println("Encounter ID " + amrsEncounters.get(0).getEncounterId() + " Visit ID " + at.getVisitId());
                }
            }
            // List<AMRSPrograms> amrsProgramsList = amrsTriageService.findByParentLocationUuid(parentUUID);

     /* JSONArray jsonObservations = new JSONArray();
      JSONObject jsonObservation = new JSONObject();
      AMRSTriage amrsTriage = amrsTriageService.
      // String puuid="a32d8e5e-fb75-419a-88aa-c2d5f37dd244";
      String pruuid="901218e9-dfc7-4afb-94cb-b32e551e4f76";
      String conceptuuid="1246AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
      jsonObservation.put("person",puuid);//"60168b73-60f1-4044-9dc6-84fdcbc1962c");
      jsonObservation.put("concept",conceptuuid);///String.valueOf(conceptsetId));
      jsonObservation.put("value", value);

      */
        }


    public static boolean isDecimal(String value) {
        try {
            Double.parseDouble(value); // Convert to double
            return value.contains("."); // Check for decimal point
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void amrsRegimenSwitch(AMRSRegimenSwitchService amrsRegimenSwitchService, AMRSTranslater amrsTranslater, String parentUUID, String locations, String auth, String url) throws JSONException, IOException {
        List<AMRSRegimenSwitch> amrsRegimenSwitchList = amrsRegimenSwitchService.findByResponseCodeIsNull();

        if(!amrsRegimenSwitchList.isEmpty()) {
            Set<String> regimenSwitchIdSet = new HashSet<>();
            List<String> distinctRegimenSwitchIds= new ArrayList<>();

            for( AMRSRegimenSwitch regimenSwitches: amrsRegimenSwitchList) {
                if(regimenSwitches.getResponseCode() == null) {
                    String encounterId = regimenSwitches.getPatientId();
                    if (regimenSwitchIdSet.add(encounterId)) {
                        distinctRegimenSwitchIds.add(encounterId);
                    }
                }
            }

            for(String encounterId: distinctRegimenSwitchIds) {
                System.out.println("Patient ID for Switches " + encounterId);

                List<AMRSRegimenSwitch> regimenSwitchList = amrsRegimenSwitchService.findByPatientId(encounterId);

                for( int x=0; x < regimenSwitchList.size(); x++ ) {

               String patient = amrsTranslater.KenyaemrPatientUuid(encounterId);
               String visitId = amrsTranslater.kenyaemrVisitUuid(regimenSwitchList.get(x).getVisitId());
                    //List<AMRSPatients> amrsPatients = patient;
                    if (visitId!="") {

                        //PLAN 1255AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                        //START 1256AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                        //CHANGE 1259AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                        //STOP DATE 1191AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                        //Reason for stoping 1252AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

                        JSONArray jsonObservations = new JSONArray();
                        JSONObject jsonObservationPType = new JSONObject();
                        jsonObservationPType.put("person", patient);
                        jsonObservationPType.put("concept", "1255AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        jsonObservationPType.put("value", "1256AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        jsonObservationPType.put("obsDatetime", regimenSwitchList.get(x).getEncounterDatetime());
                        jsonObservations.put(jsonObservationPType);


                        JSONObject jsonObservation = new JSONObject();
                        jsonObservation.put("person", patient);
                        jsonObservation.put("concept", regimenSwitchList.get(x).getKenyaemrConceptUuid());
                        jsonObservation.put("value", regimenSwitchList.get(x).getKenyaemrValue());
                        jsonObservationPType.put("obsDatetime", regimenSwitchList.get(x).getEncounterDatetime());
                        jsonObservations.put(jsonObservation);


                        JSONObject jsonRegimenSwitchEncouter = new JSONObject();
                        jsonRegimenSwitchEncouter.put("form", "da687480-e197-11e8-9f32-f2801f1b9fd1");
                        jsonRegimenSwitchEncouter.put("encounterType", "7dffc392-13e7-11e9-ab14-d663bd873d93");
                        jsonRegimenSwitchEncouter.put("location", "7dffc392-13e7-11e9-ab14-d663bd873d93");
                        jsonRegimenSwitchEncouter.put("patient", patient);
                        jsonRegimenSwitchEncouter.put("encounterDatetime", regimenSwitchList.get(x).getEncounterDatetime());
                        jsonRegimenSwitchEncouter.put("obs", jsonObservations);
                        jsonRegimenSwitchEncouter.put("visit", visitId);

                        System.out.println("Payload for is here " + jsonRegimenSwitchEncouter.toString());
                        // System.out.println("URL is here " + url + "encpunter/" + regimenSwitchList.get(0).getKenyaemrEncounterUuid());

                        OkHttpClient client = new OkHttpClient();
                        MediaType mediaType = MediaType.parse("application/json");
                        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonRegimenSwitchEncouter.toString());

                        Request request = new Request.Builder()
                                .url(url + "encounter")
                                .method("POST", body)
                                .addHeader("Authorization", "Basic " + auth)
                                .addHeader("Content-Type", "application/json")
                                .build();
                        Response response = client.newCall(request).execute();
                        String responseBody = response.body().string();
                        System.out.println("Response ndo hii " + responseBody + "More message" + response.message());

                        int resCode = response.code();
                        if (resCode == 200) {
                            for (int y = 0; y < regimenSwitchList.size(); y++) {
                                AMRSRegimenSwitch rs = regimenSwitchList.get(y);
                                rs.setResponseCode("201");
                                System.out.println("Imefika Hapa na data " + resCode);
                                amrsRegimenSwitchService.save(rs);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void patientStatus(AMRSPatientStatusService amrsPatientStatusService, String auth, String url) throws JSONException, IOException {
        List<AMRSPatientStatus> amrsPatientStatusList = amrsPatientStatusService.findByResponseCodeIsNull();
        if (amrsPatientStatusList.size() > 0) {

            // Use a Set to store unique encounter IDs
            Set<String> patientIdSet = new HashSet<>();
            List<String> distinctpatientIds = new ArrayList<>();

            // Loop through the list
            for (AMRSPatientStatus patients : amrsPatientStatusList) {
                if (patients.getResponseCode() == null) {
                    String patientId = patients.getPersonId();
                    // Add to the result list only if it hasn't been added already
                    if (patientIdSet.add(patientId)) {
                        distinctpatientIds.add(patientId);
                    }
                }
            }

            for (String patientId : distinctpatientIds) {
                System.out.println("Encounter ID for Vitals " + patientId);
                List<AMRSPatientStatus> patientStatusList = amrsPatientStatusService.findByPersonId(patientId);
                JSONArray jsonObservations = new JSONArray();
                String kenyaemrPatientUuid="";
                for (int x = 0; x < patientStatusList.size(); x++) {
                    JSONObject jsonObservation = new JSONObject();
                    jsonObservation.put("person", patientStatusList.get(x).getKenyaPatientUuid());///String.valueOf(conceptsetId));
                    jsonObservation.put("concept", patientStatusList.get(x).getKenyaEmrConceptUuid());///String.valueOf(conceptsetId));
                    jsonObservation.put("value", patientStatusList.get(x).getKenyaEmrValueUuid());
                    jsonObservation.put("obsDatetime", patientStatusList.get(x).getObsDateTime());
                    jsonObservations.put(jsonObservation);
                    kenyaemrPatientUuid= patientStatusList.get(x).getKenyaPatientUuid();
                }

                JSONObject jsonEncounter = new JSONObject();
                jsonEncounter.put("form", "add7abdc-59d1-11e8-9c2d-fa7ae01bbebc");
                jsonEncounter.put("encounterType", "de1f9d67-b73e-4e1b-90d0-036166fc6995");
                jsonEncounter.put("obs", jsonObservations);
                jsonEncounter.put("patient", kenyaemrPatientUuid);


                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());
                //RequestBody body = RequestBody.create(mediaType, jsonEncounter.toString());
                Request request = new Request.Builder()
                        .url(url + "encounter")
                        .method("POST", body)
                        .addHeader("Authorization", "Basic " + auth)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();
                String responseBody = response.body().string(); // Get the response as a string
                System.out.println("Response ndo hii " + responseBody + " More message " + response.message());

                String resBody = response.request().toString();
                int rescode = response.code();
                if (rescode == 201) {
                    for (int x = 0; x < patientStatusList.size(); x++) {
                        AMRSPatientStatus at = patientStatusList.get(x);
                        at.setResponseCode(String.valueOf(rescode));
                        at.setResponseCode("201");
                        System.out.println("Imefika Hapa na data " + rescode);
                        amrsPatientStatusService.save(at);
                    }
                }

            }


        }
    }

    public static void hivEnrollment(
            AMRSHIVEnrollmentService amrshivEnrollmentService,
            AMRSTranslater amrsTranslater,
            String locations,
            String parentUUID,
            String url,
            String auth
    ) throws JSONException, IOException {

        List<AMRSHIVEnrollment> amrshivEnrollments = amrshivEnrollmentService.findByResponseCodeIsNull();

        if (!amrshivEnrollments.isEmpty()) {
            // Use a Set to store unique patient IDs
            Set<String> patientIdSet = new HashSet<>();
            List<String> distinctPatientIds = new ArrayList<>();

            // Collect unique patient IDs
            for (AMRSHIVEnrollment enrollment : amrshivEnrollments) {
                if (enrollment.getResponseCode() == null && patientIdSet.add(enrollment.getPatientId())) {
                    distinctPatientIds.add(enrollment.getPatientId());
                }
            }

            System.out .println("list of distinct clients "+ distinctPatientIds.toString() );

            for (String patientId : distinctPatientIds) {
                System.out.println("Processing Patient ID: " + patientId);

                String kenyaemrPatientUuid = amrsTranslater.KenyaemrPatientUuid(patientId);

                List<AMRSHIVEnrollment> amrshivEnrollmentList = amrshivEnrollmentService.findByPatientId(patientId);
                String  visituuid =amrsTranslater.kenyaemrVisitUuid(amrshivEnrollmentList.get(0).getVisitId());
                String obsDateTime = "";
                // Prepare JSON observations

                JSONArray jsonObservations = new JSONArray();
                //Patient Type
                JSONObject jsonObservationPType = new JSONObject();
                jsonObservationPType.put("person", kenyaemrPatientUuid);
                jsonObservationPType.put("concept", "423c034e-14ac-4243-ae75-80d1daddce55");
                jsonObservationPType.put("value", "164144AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                jsonObservationPType.put("obsDatetime", amrshivEnrollmentList.get(0).getObsDateTime());
                jsonObservations.put(jsonObservationPType);

                 obsDateTime = amrshivEnrollmentList.get(0).getObsDateTime();

                int checkEntry=0;
                for (AMRSHIVEnrollment enrollment : amrshivEnrollmentList) {

                    if(!Objects.equals(enrollment.getConceptId(), "2155")) {
                        JSONObject jsonObservation = new JSONObject();
                        jsonObservation.put("person", kenyaemrPatientUuid);
                        jsonObservation.put("concept", enrollment.getKenyaemrConceptUuid());
                        jsonObservation.put("value", enrollment.getKenyaemrValue());
                        jsonObservation.put("obsDatetime", obsDateTime);
                      //  obsDateTime = enrollment.getObsDateTime();
                        if (!Objects.equals(enrollment.getKenyaemrConceptUuid(), "") && !Objects.equals(enrollment.getKenyaemrValue(), "")) {
                            jsonObservations.put(jsonObservation);
                        }
                        if(enrollment.getConceptId()=="6749"){
                            checkEntry=1;
                        }
                    }
                }
                if(checkEntry==0){
                    //Entry Point
                    JSONObject jsonObservationEntry = new JSONObject();
                    jsonObservationEntry.put("person", kenyaemrPatientUuid);
                    jsonObservationEntry.put("concept", "160540AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    jsonObservationEntry.put("value", "162050AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    jsonObservationEntry.put("obsDatetime", amrshivEnrollmentList.get(0).getObsDateTime());
                    jsonObservations.put(jsonObservationEntry);
                }

                // Prepare JSON encounter
                JSONObject jsonEncounter = new JSONObject();
                jsonEncounter.put("form", "e4b506c1-7379-42b6-a374-284469cba8da");
                jsonEncounter.put("encounterType", "de78a6be-bfc5-4634-adc3-5f1a280455cc");
                jsonEncounter.put("obs", jsonObservations);
                jsonEncounter.put("patient", kenyaemrPatientUuid);
                jsonEncounter.put("visit", visituuid);
                jsonEncounter.put("encounterDatetime", obsDateTime);
                jsonEncounter.put("location", "37f6bd8d-586a-4169-95fa-5781f987fe62");


                /*jsonEncounter.put("form", formuuid);
                jsonEncounter.put("patient", patintUUID);
                jsonEncounter.put("encounterDatetime", amrsVisits.get(0).getDateStarted());
                jsonEncounter.put("encounterType", etypeuuid);
                jsonEncounter.put("location", "37f6bd8d-586a-4169-95fa-5781f987fe62");
                jsonEncounter.put("visit", visituuid);
                */
                //Check if the Client is enrolled to HIV If not Enroll 1st

             /*  String statesSql = " \"states\":[{\n" +
                       "                    \"state\":\"dfdc6d40-2f2f-463d-ba90-cc97350441a8\",\n" +
                       "                            \"startDate\":\""+obsDateTime+"\"\n" +
                      " \"endDate\":\"\"\n" +
                       "                }]";


                OkHttpClient clientt = new OkHttpClient();
                MediaType mediaTypee = MediaType.parse("application/json");
                okhttp3.RequestBody bodyy = okhttp3.RequestBody.create(mediaTypee, statesSql);

                Request requestt = new Request.Builder()
                        .url(url + "programenrollment/"+kenyaemrPatientUuid)
                        .method("POST", bodyy)
                        .addHeader("Authorization", "Basic " + auth)
                        .addHeader("Content-Type", "application/json")
                        .build();

                System.out.println("Payload is Here "+ jsonEncounter.toString() );

                try (Response responsee = clientt.newCall(requestt).execute()) {
                    String responseBodyy = responsee.body().string();
                    int responseCodee = responsee.code();

                    JSONObject jsonObjectt = new JSONObject(responseBodyy);
                   // String encounterUUID = jsonObjectt.getString("uuid");


                    System.out.println("Response: " + responseBodyy + " | Status Code: " + responseCodee);
                } catch (Exception e) {
                    System.err.println("Error processing Patient ID: " + patientId + " | " + e.getMessage());
                }
                */

                    //



                // Send API request
                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());

                Request request = new Request.Builder()
                        .url(url + "encounter")
                        .method("POST", body)
                        .addHeader("Authorization", "Basic " + auth)
                        .addHeader("Content-Type", "application/json")
                        .build();

                System.out.println("Payload is Here "+ jsonEncounter.toString() );

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    int responseCode = response.code();

                    System.out.println("Response: " + responseBody + " | Status Code: " + responseCode);

                    // Update response code for successful submissions
                    if (responseCode == 201) {
                        for (AMRSHIVEnrollment enrollment : amrshivEnrollmentList) {
                            enrollment.setResponseCode("201");
                            amrshivEnrollmentService.save(enrollment);
                        }
                    } else {
                        System.err.println("Failed to process Patient ID: " + patientId + " | Status Code: " + responseCode);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing Patient ID: " + patientId + " | " + e.getMessage());
                }
            }

        } else {
            System.out.println("No enrollments found with null response codes.");
        }
    }


    public static void ordersResults(AMRSOrdersResultsService amrsOrdersResultsService, String parentUUID, String locations, String auth, String url)throws
            JSONException, IOException{
    }

    public static void artRefill(AMRSArtRefillService amrsArtRefillService,  AMRSTranslater amrsTranslater, String url, String auth) throws JSONException, IOException {

        List<AMRSArtRefill> artRefills = amrsArtRefillService.findByResponseCodeIsNull();

        if (!artRefills.isEmpty()) {
            // Use a Set to store unique patient IDs
            Set<String> visitIdSet = new HashSet<>();
            List<String> distinctVisitIds = new ArrayList<>();

            // Collect unique patient IDs
            for (AMRSArtRefill artRefill : artRefills) {
                if (artRefill.getResponseCode() == null && visitIdSet.add(artRefill.getVisitId())) {
                    distinctVisitIds.add(artRefill.getVisitId());
                }
            }

            System.out.println("list of distinct clients " + distinctVisitIds);

            for (String visitId : distinctVisitIds) {
                System.out.println("Processing Visit ID: " + visitId);

                //Fetch all obs per visitID

                List<AMRSArtRefill> artRefillList = amrsArtRefillService.findByVisitId(visitId);
                JSONArray jsonObservations = new JSONArray();
                String kenyaemrPatientUuid="";
                String kenyaemrvisitUuid="";
                String obsDateTime ="";
                for (int x =0 ;x<artRefillList.size();x++){

                    System.out.println("Loop Visits "+ visitId + " index " +x);
                    kenyaemrPatientUuid = amrsTranslater.KenyaemrPatientUuid(artRefillList.get(x).getPatientId());
                    kenyaemrvisitUuid =amrsTranslater.kenyaemrVisitUuid(visitId);
                    obsDateTime = artRefillList.get(x).getObsDateTime();
                    JSONObject jsonObservationD = new JSONObject();
                    jsonObservationD.put("person", kenyaemrPatientUuid);
                    jsonObservationD.put("concept", artRefillList.get(x).getKenyaEmrConceptUuid());
                    jsonObservationD.put("value", artRefillList.get(x).getKenyaEmrValue());
                    jsonObservationD.put("obsDatetime", artRefillList.get(x).getObsDateTime());
                    if(!Objects.equals(artRefillList.get(x).getKenyaEmrConceptUuid(), "") && !Objects.equals(artRefillList.get(x).getKenyaEmrValue(), "") ) {
                        jsonObservations.put(jsonObservationD);
                    }
                }

                JSONObject jsonEncounter = new JSONObject();
                jsonEncounter.put("form", "83fb6ab2-faec-4d87-a714-93e77a28a201");
                jsonEncounter.put("patient", kenyaemrPatientUuid);
                jsonEncounter.put("obs", jsonObservations);
                jsonEncounter.put("visit", kenyaemrvisitUuid);
                jsonEncounter.put("encounterDatetime", obsDateTime);
                jsonEncounter.put("encounterType", "e87aa2ad-6886-422e-9dfd-064e3bfe3aad");
                jsonEncounter.put("location", "37f6bd8d-586a-4169-95fa-5781f987fe62");


                // Send API request
                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());

                System.out.println("SYSTEM URL IS: " + url);

                Request request = new Request.Builder()
                        .url(url + "encounter")
                        .method("POST", body)
                        .addHeader("Authorization", "Basic " + auth)
                        .addHeader("Content-Type", "application/json")
                        .build();

                System.out.println("Payload is Here "+ jsonEncounter.toString() );

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    int responseCode = response.code();

                    System.out.println("Response: " + responseBody + " | Status Code: " + responseCode);

                    // Update response code for successful submissions
                    if (responseCode == 201) {
                        for (AMRSArtRefill artRefill : artRefillList) {
                            artRefill.setResponseCode("201");
                            amrsArtRefillService.save(artRefill);
                        }
                    } else {
                        System.err.println("Failed to process Visit ID: " + visitId + " | Status Code: " + responseCode);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing Visit ID: " + visitId + " | " + e.getMessage());
                }
            }

    }
}

public static void defaulterTracing(AMRSDefaulterTracingService amrsDefaulterTracingService, AMRSTranslater amrsTranslater, AMRSPatientServices amrsPatientServices, String url, String auth) throws  IOException, JSONException {
    List<AMRSDefaulterTracing> amrsDefaulterTracings = amrsDefaulterTracingService.findByResponseCodeIsNull();

    if (!amrsDefaulterTracings.isEmpty()) {
        // Use a Set to store unique patient IDs
        Set<String> visitIdSet = new HashSet<>();
        List<String> distinctVisitIds = new ArrayList<>();

        // Collect unique patient IDs
        for (AMRSDefaulterTracing defaulterTracing : amrsDefaulterTracings) {
            if (defaulterTracing.getResponseCode() == null && visitIdSet.add(defaulterTracing.getVisitId())) {
                distinctVisitIds.add(defaulterTracing.getVisitId());
            }
        }
        System.out.println("list of distinct clients " + distinctVisitIds);

        for (String visitId : distinctVisitIds) {
            System.out.println("Processing Visit ID: " + visitId);

            List<AMRSDefaulterTracing> amrsDefaulterTracingList = amrsDefaulterTracingService.findByVisitId(visitId);
            JSONArray jsonObservations = new JSONArray();
            String kenyaemrPatientUuid = "";
            String kenyaemrVisitUuid = "";
            String obsDatetime = "";

            // Prepare JSON observations
            for(int x=0; x<amrsDefaulterTracingList.size(); x++) {
                JSONObject jsonObservationD = new JSONObject();
                kenyaemrPatientUuid = amrsTranslater.KenyaemrPatientUuid(amrsDefaulterTracingList.get(x).getPatientId());
                kenyaemrVisitUuid = amrsTranslater.kenyaemrVisitUuid(visitId);
                obsDatetime = amrsDefaulterTracingList.get(x).getObsDatetime();
                jsonObservationD.put("person", kenyaemrPatientUuid);
                jsonObservationD.put("concept", amrsDefaulterTracingList.get(x).getKenyaEmrConceptUuid());
                jsonObservationD.put("value", amrsDefaulterTracingList.get(x).getKenyaEmrValueUuid());
                jsonObservationD.put("obsDatetime", amrsDefaulterTracingList.get(x).getObsDatetime());
                if(!Objects.equals(amrsDefaulterTracingList.get(x).getKenyaEmrConceptUuid(), "") && !Objects.equals(amrsDefaulterTracingList.get(x).getKenyaEmrValueUuid(), "") ) {
                    jsonObservations.put(jsonObservationD);
                }
            }

            JSONObject jsonEncounter = new JSONObject();

//            if(!Objects.equals(kenyaemrVisitUuid, "")) {
                jsonEncounter.put("form", "a1a62d1e-2def-11e9-b210-d663bd873d93");
                jsonEncounter.put("patient", kenyaemrPatientUuid);
                jsonEncounter.put("visit", kenyaemrVisitUuid);
                jsonEncounter.put("encounterDatetime", obsDatetime);
                jsonEncounter.put("obs", jsonObservations);
                jsonEncounter.put("encounterType", "1495edf8-2df2-11e9-b210-d663bd873d93");
                jsonEncounter.put("location", "37f6bd8d-586a-4169-95fa-5781f987fe62");
//            }

            // Send API request
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());


            Request request = new Request.Builder()
                    .url(url + "encounter")
                    .method("POST", body)
                    .addHeader("Authorization", "Basic " + auth)
                    .addHeader("Content-Type", "application/json")
                    .build();

            System.out.println("Payload is Here " + jsonEncounter);

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                int responseCode = response.code();

                System.out.println("Response: " + responseBody + " | Status Code: " + responseCode);

                // Update response code for successful submissions
                if (responseCode == 201) {
                    for (AMRSDefaulterTracing amrsDefaulterTracing : amrsDefaulterTracingList) {
                        amrsDefaulterTracing.setResponseCode("201");
                        amrsDefaulterTracingService.save(amrsDefaulterTracing);
                    }
                } else {
                    System.err.println("Failed to process visit ID: " + visitId + " | Status Code: " + responseCode);
                }
            } catch (Exception e) {
                System.err.println("Error processing visit ID: " + visitId + " | " + e.getMessage());
            }
        }

    }
}

public static void ovc(AMRSOvcService amrsOvcService, AMRSPatientServices amrsPatientServices, AMRSTranslater amrsTranslater, String url, String auth) throws JSONException, IOException, SQLException {
    List<AMRSOvc> amrsOvcs = amrsOvcService.findByResponseCodeIsNull();

    if (!amrsOvcs.isEmpty()) {
        // Use a Set to store unique patient IDs
        Set<String> visitIdSet = new HashSet<>();
        List<String> distinctVisitIds = new ArrayList<>();

        // Collect unique patient IDs
        for (AMRSOvc amrsOvc : amrsOvcs) {
            if (amrsOvc.getResponseCode() == null && visitIdSet.add(amrsOvc.getPatientId())) {
                distinctVisitIds.add(amrsOvc.getPatientId());
            }
        }

        System.out.println("list of distinct clients " + distinctVisitIds);

        for (String visitId : distinctVisitIds) {
            System.out.println("Processing visit ID: " + visitId);

            List<AMRSOvc> amrsOvcList = amrsOvcService.findByVisitId(visitId);
            JSONArray jsonObservations = new JSONArray();
            String kenyaemrPatientUuid = "";
            String kenyaemrVisitUuid = "";
            String obsDatetime = "";

            // Prepare JSON observations
            for(int x=0; x<amrsOvcList.size(); x++) {
                JSONObject jsonObservationD = new JSONObject();
                kenyaemrPatientUuid = amrsTranslater.translater(amrsOvcList.get(x).getPatientId());
                kenyaemrVisitUuid = amrsTranslater.kenyaemrVisitUuid(visitId);
                obsDatetime = amrsOvcList.get(x).getObsDateTime();
                jsonObservationD.put("person", kenyaemrPatientUuid);
                jsonObservationD.put("concept", amrsOvcList.get(0).getKenyaEmrConceptUuid());
                jsonObservationD.put("value", amrsOvcList.get(0).getKenyaEmrValue());
                jsonObservationD.put("obsDatetime", amrsOvcList.get(0).getObsDateTime());
                if(!Objects.equals(amrsOvcList.get(0).getKenyaEmrValue(), "") && !Objects.equals(amrsOvcList.get(0).getKenyaEmrConceptUuid(), "") ) {
                    jsonObservations.put(jsonObservationD);
                }
            }



            JSONObject jsonEncounter = new JSONObject();

            if(!Objects.equals(amrsOvcList.get(0).getKenyaemrVisitUuid(), "")) {
                jsonEncounter.put("form", "5cf013e8-09da-11ea-8d71-362b9e155667");
                jsonEncounter.put("patient", kenyaemrPatientUuid);
                jsonEncounter.put("obs", jsonObservations);
                jsonEncounter.put("visit", kenyaemrVisitUuid);
                jsonEncounter.put("encounterDatetime", obsDatetime);
                jsonEncounter.put("encounterType", "5cf00d9e-09da-11ea-8d71-362b9e155667");
                jsonEncounter.put("location", "37f6bd8d-586a-4169-95fa-5781f987fe62");
            }


            // Send API request
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());

            System.out.println("SYSTEM URL IS: " + url);

            Request request = new Request.Builder()
                    .url(url + "encounter")
                    .method("POST", body)
                    .addHeader("Authorization", "Basic " + auth)
                    .addHeader("Content-Type", "application/json")
                    .build();

            System.out.println("Payload is Here "+ jsonEncounter.toString() );

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                int responseCode = response.code();

                System.out.println("Response: " + responseBody + " | Status Code: " + responseCode);

                // Update response code for successful submissions
                if (responseCode == 201) {
                    for (AMRSOvc amrsOvc : amrsOvcList) {
                        amrsOvc.setResponseCode("201");
                        amrsOvcService.save(amrsOvc);
                    }
                } else {
                    System.err.println("Failed to process visit ID: " + visitId + " | Status Code: " + responseCode);
                }
            } catch (Exception e) {
                System.err.println("Error processing visit ID: " + visitId + " | " + e.getMessage());
            }
        }

    }

}
           /* for(int x =0;x<amrsPatientStatusList.size();x++) {



                AMRSPatientStatus amrsPatientStatus = amrsPatientStatusList.get(x);

                JSONObject jsonObservation = new JSONObject();
                jsonObservation.put("person", amrsPatientStatusList.get(x).getKenyaPatientUuid());///String.valueOf(conceptsetId));
                jsonObservation.put("concept", amrsPatientStatusList.get(x).getKenyaEmrConceptUuid());///String.valueOf(conceptsetId));
                jsonObservation.put("value", amrsPatientStatusList.get(x).getKenyaEmrValueUuid());
                jsonObservation.put("obsDatetime", amrsPatientStatusList.get(x).getObsDateTime());
                System.out.println("Payload for is here " + jsonObservation.toString());
               form "add7abdc-59d1-11e8-9c2d-fa7ae01bbebc"
              encounter -   de1f9d67-b73e-4e1b-90d0-036166fc6995
              //  OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
              //  okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonObservation.toString());
                //RequestBody body = RequestBody.create(mediaType, jsonEncounter.toString());
                Request request = new Request.Builder()
                        .url(url + "obs" )
                        .method("POST", body)
                        .addHeader("Authorization", "Basic " + auth)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();
                String responseBody = response.body().string(); // Get the response as a string
                System.out.println("Response ndo hii " + responseBody + " More message " + response.message());

                String resBody = response.request().toString();
                int rescode = response.code();
                if(rescode==201) {

                     amrsPatientStatus.setResponseCode("201");
                        amrsPatientStatusService.save(amrsPatientStatus);
                }
            }  */
}

