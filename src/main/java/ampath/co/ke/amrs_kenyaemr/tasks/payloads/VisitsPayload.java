package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.models.AMRSVisits;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import ampath.co.ke.amrs_kenyaemr.service.AMRSVisitService;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class VisitsPayload {
    public static void visits(AMRSVisitService amrsVisitService, AMRSPatientServices amrsPatientServices, String auth, String url) throws JSONException, ParseException, IOException {

        List<AMRSVisits> amrsVisits = amrsVisitService.getAll();
        if(amrsVisits.size() > 0) {

            for(int x  =0; x < amrsVisits.size(); x++) {
                AMRSVisits av = amrsVisits.get(x);
                List<AMRSPatients> patientId = amrsPatientServices.getByPatientID(av.getPatientId());
                String pid = patientId.get(0).getPersonId();
                String vtype = amrsVisits.get(x).getVisitType();
                String startDate = amrsVisits.get(x).getDateStarted();
                String stopDate = amrsVisits.get(x).getDateStop();

                JSONObject jsonVisit = new JSONObject();
                jsonVisit.put("patient",pid);//"60168b73-60f1-4044-9dc6-84fdcbc1962c");
                jsonVisit.put("visitType","9865c08a-97a4-4571-b873-dd422583b3a7");
                jsonVisit.put("startDatetime",startDate); //+"T06:08:25.000+0000"
                jsonVisit.put("stopDatetime",stopDate); //+"T06:09:25.000+0000"

                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, jsonVisit.toString());
                String bodyString =body.toString();
                Request request = new Request.Builder()
                        .url(url + "visit")
                        .method("POST", body)
                        .addHeader("Authorization", "Basic " + auth)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response = client.newCall(request).execute();

                // System.out.println("Response ndo hii " + jsonUser.toString());
                System.out.println("Response ndo hii " + response);
                System.out.println("Response Payload " + jsonVisit.toString());
//                av(1);
                av.setResponseCode(String.valueOf(response.code()));
                amrsVisitService.save(av);
            }
        }


    }

}
