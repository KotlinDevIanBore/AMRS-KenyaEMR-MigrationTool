package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

import ampath.co.ke.amrs_kenyaemr.models.AMRSGreenCard;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.service.AMRSGreenCardService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class GreenCardPayload {

    public static void processData(AMRSGreenCardService amrsGreenCardService, AMRSPatientServices amrsPatientServices, String url, String auth) throws JSONException, IOException {
        List<AMRSGreenCard> amrsGreenCards = amrsGreenCardService.findByResponseCodeIsNull();
        if (!amrsGreenCards.isEmpty()) {

            for (int x = 0; x < amrsGreenCards.size(); x++) {
                AMRSGreenCard amrsGreenCard = amrsGreenCards.get(x);

                List<AMRSPatients> patientsList = amrsPatientServices.getByPatientID(amrsGreenCard.getPatientId().toString());
                if (!patientsList.isEmpty()) {

                    String person = patientsList.get(0).getKenyaemrpatientUUID();
                    String concept = amrsGreenCard.getKenyaEmrConceptUuid();
                    String obsDateTime = amrsGreenCard.getObsDateTime();
                    String encounter = amrsGreenCard.getKenyaEmrEncounterUuid();

                  String  value = amrsGreenCard.getValueCoded()+amrsGreenCard.getValueDatetime()+amrsGreenCard.getValueNumeric()+amrsGreenCard.getValueText();

                    System.out.println(x+"===========================>>>>>>>>value is : "+value);
                    JSONObject obj = new JSONObject();

                    obj.put("person", person);
                    obj.put("concept", concept);
                    obj.put("encounter", encounter);
                    obj.put("obsDatetime", obsDateTime);
                    obj.put("value", value);

                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, obj.toString());
                    String bodyString = body.toString();
                    Request request = new Request.Builder()
                            .url(url + "obs")
                            .method("POST", body)
                            .addHeader("Authorization", "Basic " + auth)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    Response response = client.newCall(request).execute();

                    String responseBody = response.body().string(); // Get the response as a string
                    JSONObject jsonObject = new JSONObject(responseBody);
                    if (response.code() == 201) {
                        String tcaUuid = jsonObject.getString("uuid");
                        amrsGreenCard.setKenyaEmrTcaUuid(tcaUuid);
                        amrsGreenCard.setResponseCode(String.valueOf(response.code()));
                        amrsGreenCardService.save(amrsGreenCard);
                        System.out.println("saved successfully");
                    }
                else {
                        System.out.println("failed to save data");
                        System.out.println(obj.toString());

                    }

                }
            }


        }

    }
}


