package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPrograms;
import ampath.co.ke.amrs_kenyaemr.models.AMRSTriage;
import ampath.co.ke.amrs_kenyaemr.service.AMRSProgramService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSTriageService;
import ampath.co.ke.amrs_kenyaemr.tasks.Mappers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class CareOpenMRSPayload {
    public static void programs(AMRSProgramService amrsProgramService, String locations, String parentUUID,String url, String auth ) throws JSONException, IOException {
       List<AMRSPrograms> amrsProgramsList = amrsProgramService.findByParentLocationUuid(parentUUID);
       if(amrsProgramsList.size()>0) {
       JSONObject jsonProgram = new JSONObject();
        for(int x=0;x<amrsProgramsList.size();x++) {

            String programms = Mappers.programs(String.valueOf(amrsProgramsList.get(x).getProgramUUID()));
            System.out.println("Program UUID is here " + programms + " amrs UUID " + amrsProgramsList.get(x).getProgramUUID());
            int pid = amrsProgramsList.get(x).getProgramID();
            AMRSPrograms ap = amrsProgramsList.get(x);

           // if (ap.getResponseCode() != 201) {

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
                    ap.setResponseCode(rescode);
                    System.out.println("Imefika Hapa na data " + rescode);
                } else {
                    //ap.setResponseCode();
                }
                amrsProgramService.save(ap);

           // }
        }

       }
    }
  public static void triage(AMRSTriageService amrsTriageService, String locations, String parentUUID, String url, String auth ) throws JSONException, IOException {
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
