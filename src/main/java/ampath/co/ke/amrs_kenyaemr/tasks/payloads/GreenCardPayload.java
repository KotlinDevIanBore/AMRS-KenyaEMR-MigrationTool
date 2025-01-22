package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

import ampath.co.ke.amrs_kenyaemr.methods.AMRSTranslater;
import ampath.co.ke.amrs_kenyaemr.models.*;
import ampath.co.ke.amrs_kenyaemr.service.AMRSGreenCardService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class GreenCardPayload {

    public static void processGreenCard(AMRSGreenCardService amrsGreenCardService, AMRSPatientServices amrsPatientServices, AMRSTranslater amrsTranslater, String KenyaEMRlocationUuid, String url, String auth) throws JSONException, IOException {
        List<AMRSGreenCard> amrsGreenCards = amrsGreenCardService.findByResponseCodeIsNull();
        if (amrsGreenCards.size() > 0) {
            // Use a Set to store unique encounter IDs
            Set<String> visistIdSet = new HashSet<>();
            List<String> distinctVisitIds = new ArrayList<>();

            // Loop through the list
            for (AMRSGreenCard greenCard : amrsGreenCards) {
                if (greenCard.getResponseCode() == null) {
                    String visitId = greenCard.getVisitId();
                    // Add to the result list only if it hasn't been added already
                    if (visistIdSet.add(visitId)) {
                        distinctVisitIds.add(visitId);
                    }
                }
            }

            for (String visitId : distinctVisitIds) {
                System.out.println("VisitId ID for GreenCard " + visitId);
                List<AMRSGreenCard> amrsGreenCardEncounters = amrsGreenCardService.findByVisitId(visitId);
                JSONArray jsonObservations = new JSONArray();
                String patientuuid = "";
                String formuuid = "";
                String encounteruuid = "";
                String encounterDatetime = "";
                String obsDatetime = "";
                String visituuid = amrsTranslater.kenyaemrVisitUuid(visitId);
                // String locationuuid= amrsTranslater.location()
                System.out.println("VisitId ID for GreenCard " + visitId+ " Concepts Questions "+ amrsGreenCardEncounters.size());
                for (int x = 0; x < amrsGreenCardEncounters.size(); x++) {
                    JSONObject jsonObservation = new JSONObject();
                    String value = amrsGreenCardEncounters.get(x).getKenyaEmrValue();
                    obsDatetime = amrsGreenCardEncounters.get(x).getObsDateTime();
                    jsonObservation.put("person", amrsTranslater.KenyaemrPatientUuid(amrsGreenCardEncounters.get(x).getPatientId()));///String.valueOf(conceptsetId));
                    jsonObservation.put("concept", amrsGreenCardEncounters.get(x).getKenyaEmrConceptUuid());///String.valueOf(conceptsetId));
                    jsonObservation.put("obsDatetime", amrsGreenCardEncounters.get(x).getObsDateTime());///String.valueOf(conceptsetId));
                    jsonObservation.put("value", amrsGreenCardEncounters.get(x).getKenyaEmrValue());
                    jsonObservation.put("location", KenyaEMRlocationUuid);
                    Set<String> excludedIds = Set.of("10102", "10103","10104","10105","10106","10107","10108","10109","1645");
                    if (!Objects.equals(value, "")) {
                        if (!excludedIds.contains(amrsGreenCardEncounters.get(x).getConceptId())) {
                            jsonObservations.put(jsonObservation);
                        }
                    }
                    patientuuid = amrsTranslater.KenyaemrPatientUuid(amrsGreenCardEncounters.get(x).getPatientId());
                    formuuid = amrsGreenCardEncounters.get(x).getKenyaemrFormUuid();
                    encounteruuid = amrsGreenCardEncounters.get(x).getKenyaemrEncounterTypeUuid();
                    encounterDatetime = amrsGreenCardEncounters.get(x).getKenyaEmrEncounterDateTime();

                }
               // Visit Scheduled? - True
                JSONObject jsonObservationD = new JSONObject();
                jsonObservationD.put("person", patientuuid);
                jsonObservationD.put("concept", "1246AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                jsonObservationD.put("value", "true");
                jsonObservationD.put("location", KenyaEMRlocationUuid);
                jsonObservationD.put("obsDatetime", obsDatetime);
                jsonObservations.put(jsonObservationD);

                //Visited BY -  deafault self
                JSONObject jsonObservationEntry = new JSONObject();
                jsonObservationEntry.put("person", patientuuid);
                jsonObservationEntry.put("concept", "161643AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                jsonObservationEntry.put("value", "978AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                jsonObservationEntry.put("location", KenyaEMRlocationUuid);
                jsonObservationEntry.put("obsDatetime", obsDatetime);
                jsonObservations.put(jsonObservationD);

                //Pop Type
                JSONObject jsonObservationPop = new JSONObject();
                jsonObservationPop.put("person", patientuuid);
                jsonObservationPop.put("concept", "cf543666-ce76-4e91-8b8d-c0b54a436a2e");
                jsonObservationPop.put("value", "5d308c8c-ad49-45e1-9885-e5d09a8e5587");
                jsonObservationPop.put("location", KenyaEMRlocationUuid);
                jsonObservationPop.put("obsDatetime", obsDatetime);
                jsonObservations.put(jsonObservationD);


                //Publish the data to KenyaEMR
                if (!Objects.equals(visituuid, "")) {
                    JSONObject jsonEncounter = new JSONObject();
                    jsonEncounter.put("form", formuuid);
                    jsonEncounter.put("patient", patientuuid);
                    jsonEncounter.put("encounterDatetime", encounterDatetime);
                    jsonEncounter.put("encounterType", encounteruuid);
                    jsonEncounter.put("location", KenyaEMRlocationUuid);
                    jsonEncounter.put("visit", visituuid);
                    jsonEncounter.put("obs", jsonObservations);

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
                        for (int x = 0; x < amrsGreenCardEncounters.size(); x++) {
                            AMRSGreenCard at = amrsGreenCardEncounters.get(x);
                            at.setResponseCode(String.valueOf(rescode));
                            at.setResponseCode("201");
                            // at.setKenyaemrEncounterUuid(amrsTriageEncounters.get(0).getKenyaemrEncounterUuid());
                            System.out.println("Imefika Hapa na data " + rescode);
                            amrsGreenCardService.save(at);
                        }
                    }else{
                        for (int x = 0; x < amrsGreenCardEncounters.size(); x++) {
                            AMRSGreenCard at = amrsGreenCardEncounters.get(x);
                            at.setResponseCode(String.valueOf(rescode));
                            at.setResponseCode("400");
                            // at.setKenyaemrEncounterUuid(amrsTriageEncounters.get(0).getKenyaemrEncounterUuid());
                            System.out.println("Imefika Hapa na data " + rescode);
                            amrsGreenCardService.save(at);
                        }
                    }
                }
            }
        }
    }
}


