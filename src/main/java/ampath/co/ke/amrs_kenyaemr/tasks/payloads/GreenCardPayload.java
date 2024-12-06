package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

import ampath.co.ke.amrs_kenyaemr.models.AMRSGreenCard;
import ampath.co.ke.amrs_kenyaemr.models.AMRSHIVEnrollment;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.service.AMRSGreenCardService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class GreenCardPayload {

    public static void processData(AMRSGreenCardService amrsGreenCardService, AMRSPatientServices amrsPatientServices, String url, String auth) throws JSONException, IOException {

        List<AMRSGreenCard> amrsEncounters = amrsGreenCardService.findDistinctEncounters();
        String person = "",kenyaEMREncounterUuid="";
        for (AMRSGreenCard uniqueEncounter : amrsEncounters) {
            List<AMRSGreenCard> amrsGreenCards = amrsGreenCardService.findByEncounterId(uniqueEncounter.getEncounterId());
            JSONArray jsonObservationsList = new JSONArray();
        if (!amrsGreenCards.isEmpty()) {
            List<AMRSPatients> patientsList = amrsPatientServices.getByPatientID(amrsGreenCards.get(0).getPatientId().toString());
            if (!patientsList.isEmpty()) {
                person =patientsList.get(0).getKenyaemrpatientUUID();
            }

            kenyaEMREncounterUuid = amrsGreenCards.get(0).getKenyaEmrEncounterUuid();


            for (int x = 0; x < amrsGreenCards.size(); x++) {
                JSONObject objObservations = new JSONObject();
                AMRSGreenCard amrsGreenCard = amrsGreenCards.get(x);

                    String concept = amrsGreenCard.getKenyaEmrConceptUuid();
                    String obsDateTime = amrsGreenCard.getObsDateTime();
                    String encounter = amrsGreenCard.getKenyaEmrEncounterUuid();

                    String value = amrsGreenCard.getValueCoded() + amrsGreenCard.getValueDatetime() + amrsGreenCard.getValueNumeric() + amrsGreenCard.getValueText();

                    System.out.println(x + "===========================>>>>>>>>value is : " + value);

                    objObservations.put("person", person);
                    objObservations.put("concept", concept);
                    objObservations.put("encounter", encounter);
                    objObservations.put("obsDatetime", obsDateTime);
                    objObservations.put("value", value);

                    jsonObservationsList.put(objObservations);
            }


        }
//        ---- end of inner loop

            // Prepare JSON encounter
            JSONObject jsonEncounter = new JSONObject();
            jsonEncounter.put("form", "22c68f86-bbf0-49ba-b2d1-23fa7ccf0259");
            jsonEncounter.put("encounterType", "a0034eee-1940-4e35-847f-97537a35d05e");
            jsonEncounter.put("obs", jsonObservationsList);
            jsonEncounter.put("patient", person);


            // Send API request
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());

            Request request = new Request.Builder()
                    .url(url + "encounter/"+kenyaEMREncounterUuid)
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
                if (responseCode == 200 || responseCode == 201) {
                    for (AMRSGreenCard amrsGreenCard : amrsGreenCards) {
                        amrsGreenCard.setResponseCode("201");
                        amrsGreenCardService.save(amrsGreenCard);
                    }

                    System.out.println("success in processing patient ID "+person+" responseCode");
                } else {
                    System.err.println("Failed to process Patient ID: " + person + " | Status Code: " + responseCode);
                }
            } catch (Exception e) {
                System.err.println("Error processing Patient ID: " + person + " | " + e.getMessage());
            }

    }

    }
}


