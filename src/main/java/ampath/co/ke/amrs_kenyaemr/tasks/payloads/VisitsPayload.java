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
    public static void visits(AMRSVisitService amrsVisitService,String kenyaEMRLocationUuid, AMRSPatientServices amrsPatientServices, String auth, String url) throws JSONException, ParseException, IOException {

        List<AMRSVisits> amrsVisits = amrsVisitService.findByResponseCodeIsNull();
        if(!amrsVisits.isEmpty()) {

            for (int x = 0; x < amrsVisits.size(); x++) {
                AMRSVisits av = amrsVisits.get(x);
                if (av.getKenyaemrVisitUuid() == null) {
                    List<AMRSPatients> patientsList = amrsPatientServices.getByPatientID(av.getPatientId());
                    if (!patientsList.isEmpty()) {
                        String pid = patientsList.get(0).getKenyaemrpatientUUID();
                        System.out.println("Visits person_ID "+ av.getPatientId()  +"Patient in KenyaEMR "+pid +" AMRS Persionid "+patientsList.get(0).getKenyaemrpatientUUID() );

                        if (pid !=null) {
                            String vtype = amrsVisits.get(x).getVisitType();
                            String startDate = amrsVisits.get(x).getDateStarted();
                            String stopDate = amrsVisits.get(x).getDateStop();
                            JSONObject jsonVisit = new JSONObject();
                            jsonVisit.put("patient", pid);//"60168b73-60f1-4044-9dc6-84fdcbc1962c");
                            jsonVisit.put("visitType", "3371a4d4-f66f-4454-a86d-92c7b3da990c");
                            jsonVisit.put("location", kenyaEMRLocationUuid);
                            jsonVisit.put("startDatetime", startDate); //+"T06:08:25.000+0000"
                            jsonVisit.put("stopDatetime", stopDate); //+"T06:09:25.000+0000"

                            OkHttpClient client = new OkHttpClient();
                            MediaType mediaType = MediaType.parse("application/json");
                            RequestBody body = RequestBody.create(mediaType, jsonVisit.toString());
                            String bodyString = body.toString();
                            Request request = new Request.Builder()
                                    .url(url + "visit")
                                    .method("POST", body)
                                    .addHeader("Authorization", "Basic " + auth)
                                    .addHeader("Content-Type", "application/json")
                                    .build();
                            Response response = client.newCall(request).execute();

                            String responseBody = response.body().string(); // Get the response as a string
                            System.out.println("Response ndo hii " + responseBody + " More message " + response.message() + " reponse code " + response.code());
                            JSONObject jsonObject = new JSONObject(responseBody);

                            // System.out.println("Response ndo hii " + jsonUser.toString());
                            System.out.println("Response ndo hii " + response);
                            System.out.println("Response Payload " + jsonVisit.toString());
                            if (response.code() == 201) {
                                String visitUuid = jsonObject.getString("uuid");
                                av.setKenyaemrVisitUuid(visitUuid);
                                av.setResponseCode(String.valueOf(response.code()));
                                amrsVisitService.save(av);
                            }
                        }
                        /*else{
                            System.out.println("Patient is Missing");
                            av.setKenyaemrPatientUuid("Patient is Missing");
                            av.setResponseCode("400");
                        }
                        amrsVisitService.save(av);*/
                    }
                }else{
                    System.out.println("Visit Information exists");
                }
            }
        }

    }

}
