package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CareOpenMRSPayload {
    public static void programs(AMRSProgramService amrsProgramService, String locations, String parentUUID, String url, String auth) throws JSONException, IOException {
        //List<AMRSPrograms> amrsProgramsList = amrsProgramService.findByParentLocationUuid(parentUUID);
        List<AMRSPrograms> amrsProgramsList = amrsProgramService.findByResponseCodeIsNull();
        if (amrsProgramsList.size() > 0) {
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

    public static void triage(AMRSTriageService amrsTriageService, AMRSPatientServices amrsPatientServices, AMRSEncounterService amrsEncounterService, String url, String auth) throws JSONException, IOException {

        List<AMRSTriage> amrsTriages = amrsTriageService.findByResponseCodeIsNull();
        if (amrsTriages.size() > 0) {

            // Use a Set to store unique encounter IDs
            Set<String> encounterIdSet = new HashSet<>();
            List<String> distinctEncounterIds = new ArrayList<>();

            // Loop through the list
            for (AMRSTriage triage : amrsTriages) {
                if (triage.getResponseCode() == null) {
                    String encounterId = triage.getEncounterId();
                    // Add to the result list only if it hasn't been added already
                    if (encounterIdSet.add(encounterId)) {
                        distinctEncounterIds.add(encounterId);
                    }
                }
            }

            for (String encounterId : distinctEncounterIds) {
                System.out.println("Encounter ID for Vitals " + encounterId);
                List<AMRSTriage> amrsTriageEncounters = amrsTriageService.findByEncounterId(encounterId);
                //AMRSTriage at = amrsTriageEncounters.get(0);
                JSONArray jsonObservations = new JSONArray();

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
                      jsonObservation.put("person",amrsTriageEncounters.get(x).getKenyaemrPatientUuid());///String.valueOf(conceptsetId));
                      jsonObservation.put("concept",amrsTriageEncounters.get(x).getKenyaemConceptId());///String.valueOf(conceptsetId));
                      jsonObservation.put("obsDatetime",amrsTriageEncounters.get(x).getObsDateTime());///String.valueOf(conceptsetId));
                      jsonObservation.put("value", amrsTriageEncounters.get(x).getValue());
                    jsonObservations.put(jsonObservation);
                }

                System.out.println("Payload for is here " + jsonObservations.toString());

                List<AMRSEncounters> amrsEncounters = amrsEncounterService.findByEncounterId(encounterId);
                if (amrsEncounters.size() > 0) {
                    JSONObject jsonEncounter = new JSONObject();
                    jsonEncounter.put("form", "37f6bd8d-586a-4169-95fa-5781f987fe62");
                    jsonEncounter.put("obs", jsonObservations);
                    System.out.println("Payload for is here " + jsonEncounter.toString());
                    System.out.println("URL is here " + url + "encounter/" + amrsEncounters.get(0).getKenyaemrEncounterUuid());

                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");
                    okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());
                    //RequestBody body = RequestBody.create(mediaType, jsonEncounter.toString());
                    Request request = new Request.Builder()
                            .url(url + "encounter/" + amrsEncounters.get(0).getKenyaemrEncounterUuid())
                            .method("POST", body)
                            .addHeader("Authorization", "Basic " + auth)
                            .addHeader("Content-Type", "application/json")
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseBody = response.body().string(); // Get the response as a string
                    System.out.println("Response ndo hii " + responseBody + " More message " + response.message());

                    String resBody = response.request().toString();
                    int rescode = response.code();
                    System.out.println("Response Code Hapa " + rescode );

                    if (rescode == 200) {
                        for (int x = 0; x < amrsTriageEncounters.size(); x++) {
                            AMRSTriage at = amrsTriageEncounters.get(x);
                            at.setResponseCode(String.valueOf(rescode));
                            at.setResponseCode("201");
                            at.setKenyaemrEncounterUuid(amrsEncounters.get(0).getKenyaemrEncounterUuid());
                            System.out.println("Imefika Hapa na data " + rescode);
                            amrsTriageService.save(at);
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
    }

    public static boolean isDecimal(String value) {
        try {
            Double.parseDouble(value); // Convert to double
            return value.contains("."); // Check for decimal point
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void amrsRegimenSwitch(AMRSRegimenSwitchService amrsRegimenSwitchService, String parentUUID, String locations, String auth, String url) {
    }

    public static void patientStatus(AMRSPatientStatusService amrsPatientStatusService, String parentUUID, String locations, String auth, String url) throws JSONException, IOException {
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

    public static void ordersResults(AMRSOrdersResultsService amrsOrdersResultsService, String parentUUID, String locations, String auth, String url) {
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
