package ampath.co.ke.amrs_kenyaemr.tasks.payloads;


import ampath.co.ke.amrs_kenyaemr.models.AMRSObs;
import ampath.co.ke.amrs_kenyaemr.service.AMRSObsService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class ObsPayload {
    public  static void obs(AMRSObsService amrsObsService, AMRSPatientServices amrsPatientServices , String url, String auth, List<AMRSObs> observations) throws JSONException, IOException {
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
    }
}
