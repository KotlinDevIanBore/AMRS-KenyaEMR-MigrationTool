package ampath.co.ke.amrs_kenyaemr.tasks.payloads;


import ampath.co.ke.amrs_kenyaemr.models.AMRSEncounters;
import ampath.co.ke.amrs_kenyaemr.models.AMRSObs;
import ampath.co.ke.amrs_kenyaemr.models.AMRSTriage;
import ampath.co.ke.amrs_kenyaemr.service.AMRSEncounterService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSObsService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    public static void newObs(AMRSObsService amrsObsService, AMRSPatientServices amrsPatientServices, AMRSEncounterService amrsEncounterService, String url, String auth) throws JSONException, IOException {
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
                    List<AMRSObs> amrsObs = amrsObsService.findByEncounterId(encounterId);
                    //AMRSTriage at = amrsTriageEncounters.get(0);
                    JSONArray jsonObservations = new JSONArray();
                    for (int x = 0; x < amrsObs.size(); x++) {
                        JSONObject jsonObservation = new JSONObject();
                        String dataType = amrsObs.get(x).getDataType();
                        jsonObservation.put("person", amrsObs.get(x).getKenyaemrpersonuuid());///String.valueOf(conceptsetId));
                        jsonObservation.put("concept", amrsObs.get(x).getKenyaemrconceptuuid());///String.valueOf(conceptsetId));
                        jsonObservation.put("obsDatetime", amrsObs.get(x).getObsDatetime());///String.valueOf(conceptsetId));
                        if(dataType.equals("2")){
                            jsonObservation.put("value", amrsObs.get(x).getKenyaemrvalue());
                        }else if (dataType.equals("1")){

                            jsonObservation.put("value", Double.parseDouble(amrsObs.get(x).getKenyaemrvalue()));
                        }
                        else if (dataType.equals("10")) {
                            Boolean responsevalue =false;
                            if(amrsObs.get(x).getKenyaemrvalue().equals("1065")){
                                responsevalue = true;
                            }
                            jsonObservation.put("value", responsevalue);

                        }
                        else{
                            jsonObservation.put("value", amrsObs.get(x).getKenyaemrvalue());
                        }
                        if((!Objects.equals(amrsObs.get(x).getKenyaemrconceptuuid(), ""))) {

                            jsonObservations.put(jsonObservation);
                        }

                      //  }
                    }
                    List<AMRSEncounters> amrsEncounters = amrsEncounterService.findByEncounterId(encounterId);
                    if (amrsEncounters.size() > 0) {
                        JSONObject jsonEncounter = new JSONObject();
                        // jsonEncounter.put("form", "37f6bd8d-586a-4169-95fa-5781f987fe62");
                       // String
                        jsonEncounter.put("obs", jsonObservations);
                        System.out.println("Payload for is here " + jsonEncounter.toString());
                        System.out.println("URL is here " + url + "encounter/" + amrsEncounters.get(0).getKenyaemrEncounterUuid());
                        //System.out.println("Processing observation: " + generateObsPayload(observation));
                        OkHttpClient client = new OkHttpClient();
                        MediaType mediaType = MediaType.parse("application/json");
                        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());
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
                        if (rescode == 200) {
                            for (int x = 0; x < amrsObs.size(); x++) {
                                AMRSObs at = amrsObs.get(x);
                                at.setResponseCode(String.valueOf(rescode));
                                at.setResponseCode("201");
                                // at.setKenyaemrEncounterUuid(amrsEncounters.get(0).getKenyaemrEncounterUuid());
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

