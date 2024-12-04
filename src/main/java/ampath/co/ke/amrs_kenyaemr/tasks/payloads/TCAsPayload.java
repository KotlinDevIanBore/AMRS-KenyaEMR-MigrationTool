package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.models.AMRSTcas;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import ampath.co.ke.amrs_kenyaemr.service.AMRSTCAService;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class TCAsPayload {

    public static void tcas(AMRSTCAService amrstcaService, AMRSPatientServices amrsPatientServices, String url, String auth) throws JSONException, IOException {
        List<AMRSTcas> amrsTcas = amrstcaService.findByResponseCodeIsNull();
        if (!amrsTcas.isEmpty()) {

            for (int x = 0; x < amrsTcas.size(); x++) {
                AMRSTcas amrsTCA = amrsTcas.get(x);

                List<AMRSPatients> patientsList = amrsPatientServices.getByPatientID(amrsTCA.getPatientId().toString());
                if (!patientsList.isEmpty()) {

                    String person = patientsList.get(0).getKenyaemrpatientUUID();
                    String concept = amrsTCA.getKenyaEmrConceptUuid();
                    String value = amrsTCA.getTca();
                    String obsDateTime = amrsTCA.getObsDateTime();
                    String encounter = amrsTCA.getKenyaEmrEncounterUuid();


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
                        amrsTCA.setKenyaEmrTcaUuid(tcaUuid);
                        amrsTCA.setResponseCode(String.valueOf(response.code()));
                        amrstcaService.save(amrsTCA);

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


