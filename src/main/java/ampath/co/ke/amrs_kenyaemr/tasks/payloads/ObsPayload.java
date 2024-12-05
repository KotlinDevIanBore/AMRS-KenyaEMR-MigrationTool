package ampath.co.ke.amrs_kenyaemr.tasks.payloads;


import ampath.co.ke.amrs_kenyaemr.methods.AMRSTranslater;
import ampath.co.ke.amrs_kenyaemr.models.AMRSEncounters;
import ampath.co.ke.amrs_kenyaemr.models.AMRSObs;
import ampath.co.ke.amrs_kenyaemr.models.AMRSTriage;
import ampath.co.ke.amrs_kenyaemr.service.AMRSEncounterService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSObsService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class ObsPayload {
   /* public  static void obs(AMRSObsService amrsObsService, AMRSPatientServices amrsPatientServices , String url, String auth, List<AMRSObs> observations) throws JSONException, IOException {
        // Logic to process or send observations
        List<AMRSObs> amrsObsList = amrsObsService.findByResponseCodeIsNull();
        System.out.println("imekejua hapa");
        if(!amrsObsList.isEmpty()) {
            for (AMRSObs observation : observations) {
                System.out.println("Processing observation: " + generateObsPayload(observation));
                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, generateObsPayload(observation));
                Request request = new Request.Builder()
                        .url(url + "obs")
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
                    observation.setResponseCode("201");
                }
            }
        }
    }

    private static String generateObsPayload(AMRSObs observation) {
        return "{\n" +
                "  \"person\": \"" + observation.getKenyaemrpersonuuid() + "\",\n" +
                "  \"concept\": \"" + observation.getKenyaemrconceptuuid() + "\",\n" +
                "  \"obsDatetime\": \"" + observation.getObsDatetime() + "\",\n" +
                "  \"value\": \"" + observation.getValue() + "\",\n" +
                "  \"encounter\": \"" + observation.getKenyaemrencounteruuid() + "\"\n" +
                "}";
    } */

    public static void newObs(AMRSObsService amrsObsService, AMRSTranslater amrsTranslater, AMRSPatientServices amrsPatientServices, AMRSEncounterService amrsEncounterService, String url, String auth) throws JSONException, IOException {
        // Logic to process or send observations
        List<AMRSObs> amrsObsList = amrsObsService.findByResponseCodeIsNull();
        System.out.println("imekejua hapa");
        if (!amrsObsList.isEmpty()) {
            for (int y = 0; y < amrsObsList.size(); y++) {

                Set<String> encounterIdSet = new HashSet<>();
                List<String> distinctEncounterIds = new ArrayList<>();

                // Loop through the list
                for (AMRSObs amrsObs : amrsObsList) {
                    if (amrsObs.getResponseCode() == null) {
                        String encounterId = amrsObs.getEncounterId();
                        // Add to the result list only if it hasn't been added already
                        if (encounterIdSet.add(encounterId)) {
                            distinctEncounterIds.add(encounterId);
                        }
                    }
                }

                for (String encounterId : distinctEncounterIds) {
                    System.out.println("Encounter ID for Vitals " + encounterId);
                    //Payload
                    List<AMRSObs> amrsObsEncounter = amrsObsService.findByEncounterId(encounterId);
                    //AMRSTriage at = amrsTriageEncounters.get(0);
                    JSONArray jsonObservations = new JSONArray();
                    String formuuid = "";
                    String patientUuid="";
                    for (int x = 0; x < amrsObsEncounter.size(); x++) {
                        JSONObject jsonObservation = new JSONObject();
                        String dataType = amrsObsEncounter.get(x).getDataType();
                        jsonObservation.put("person", amrsObsEncounter.get(x).getKenyaemrpersonuuid());///String.valueOf(conceptsetId));
                        jsonObservation.put("concept", amrsObsEncounter.get(x).getKenyaemrconceptuuid());///String.valueOf(conceptsetId));
                        jsonObservation.put("obsDatetime", amrsObsEncounter.get(x).getObsDatetime());///String.valueOf(conceptsetId));
                       System.out.println("Datatype ID "+ dataType );
                        patientUuid = amrsObsEncounter.get(x).getKenyaemrpersonuuid();
                        if(Objects.equals(dataType, "2")){
                            jsonObservation.put("value", amrsObsEncounter.get(x).getKenyaemrvalue());
                        }else if (Objects.equals(dataType,"1")){

                            jsonObservation.put("value", Double.parseDouble(amrsObsEncounter.get(x).getKenyaemrvalue()));
                        }
                        else if (Objects.equals(dataType,"10")) {
                            Boolean responsevalue =false;
                            if(amrsObsEncounter.get(x).getKenyaemrvalue().equals("1065")){
                                responsevalue = true;
                            }
                            jsonObservation.put("value", responsevalue);

                        }
                        else{
                            jsonObservation.put("value", amrsObsEncounter.get(x).getKenyaemrvalue());
                        }
                        if((!Objects.equals(amrsObsEncounter.get(x).getKenyaemrconceptuuid(), ""))) {

                            jsonObservations.put(jsonObservation);
                        }

                        formuuid = amrsTranslater.formtranslater(amrsObsEncounter.get(x).getFormId());


                        //  }
                    }
                    List<AMRSEncounters> amrsEncounters = amrsEncounterService.findByEncounterId(encounterId);
                    if (amrsEncounters.size() > 0) {
                        JSONObject jsonEncounter = new JSONObject();
                        // jsonEncounter.put("form", "37f6bd8d-586a-4169-95fa-5781f987fe62");
                        // String
                        jsonEncounter.put("obs", jsonObservations);
                        jsonEncounter.put("form", formuuid);
                        // jsonEncounter.put("patient,",patientUuid);
                       //  jsonEncounter.put("encounterType","a0034eee-1940-4e35-847f-97537a35d05e");

                        System.out.println("Payload for is here " + jsonEncounter.toString());
                        System.out.println("URL is here " + url + "encounter/" + amrsEncounters.get(0).getKenyaemrEncounterUuid());
                        //System.out.println("Processing observation: " + generateObsPayload(observation));
                        if (!Objects.equals(formuuid, "")) {
                            OkHttpClient client = new OkHttpClient();
                            MediaType mediaType = MediaType.parse("application/json");
                            RequestBody body = RequestBody.create(mediaType, jsonEncounter.toString());
                            String bodyString = body.toString();
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
                            System.out.println("Imefika Hapa na data " + rescode);
                            if (rescode == 200) {
                                for (int x = 0; x < amrsObsEncounter.size(); x++) {
                                    AMRSObs at = amrsObsEncounter.get(x);
                                    at.setResponseCode(String.valueOf(rescode));
                                    at.setResponseCode("201");
                                    System.out.println("Imefika Hapa na data " + rescode);
                                    amrsObsService.save(at);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

