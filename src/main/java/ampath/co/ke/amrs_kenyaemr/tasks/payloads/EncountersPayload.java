package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncounters;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.models.AMRSUsers;
import ampath.co.ke.amrs_kenyaemr.models.AMRSVisits;
import ampath.co.ke.amrs_kenyaemr.service.AMRSEncounterService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import ampath.co.ke.amrs_kenyaemr.service.AMRSUserServices;
import ampath.co.ke.amrs_kenyaemr.service.AMRSVisitService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class EncountersPayload {
    public  static void encounters(AMRSEncounterService amrsEncounterService, AMRSPatientServices amrsPatientServices, AMRSVisitService amrsVisitService, String url, String auth) throws JSONException, IOException {
        List<AMRSEncounters> amrsEncountersList = amrsEncounterService.findByResponseCodeIsNull();
        if(amrsEncountersList.size()>0){

            for(int x=0;x<amrsEncountersList.size();x++){
                AMRSEncounters ae = amrsEncountersList.get(x);
                String patientId = amrsEncountersList.get(x).getPatientId();
                List<AMRSPatients> patientsList = amrsPatientServices.getByPatientID(patientId);
                if(patientsList.size()>0){
                    JSONObject jsonEncounter = new JSONObject();
                    if(ae.getVisitId()!=null){
                        List<AMRSVisits> amrsVisits = amrsVisitService.findByVisitID(ae.getVisitId());
                        if(amrsVisits.size()>0){
                            if(amrsVisits.get(0).getResponseCode().equals("201")) {
                                jsonEncounter.put("patient", patientsList.get(0).getKenyaemrpatientUUID());
                                jsonEncounter.put("encounterType", ae.getKenyaemrEncounterUuid());
                                jsonEncounter.put("location", "c55535b8-b9f2-4a97-8c6c-4ea9496256df");
                                jsonEncounter.put("encounterDatetime", ae.getEncounterDateTime());
                                jsonEncounter.put("encounterType", ae.getKenyaemrEncounterTypeUuid());
                                jsonEncounter.put("visit", amrsVisits.get(0).getKenyaemrVisitUuid());
                            }

                        }

                    }else {

                        jsonEncounter.put("patient", patientsList.get(0).getKenyaemrpatientUUID());
                        jsonEncounter.put("encounterType", ae.getKenyaemrEncounterUuid());
                        jsonEncounter.put("location", "3e6261cc-ad5e-4834-b85d-af8b42a133e4");
                        jsonEncounter.put("encounterType",ae.getKenyaemrEncounterTypeUuid());
                        jsonEncounter.put("encounterDatetime", ae.getEncounterDateTime());
                    }
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");
                    okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());
                    Request request = new Request.Builder()
                            .url(url + "encounter")
                            .method("POST", body)
                            .addHeader("Authorization", "Basic " + auth)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String resBody = response.request().toString();
                    String responseBody = response.body().string();
                    System.out.println("System logs "+ resBody );
                    System.out.println("System Payload "+ jsonEncounter.toString() );
                    JSONObject jsonObject = new JSONObject(responseBody);
                    int rescode = response.code();
                    if (response.code() == 201) {
                        String encounterUuid = jsonObject.getString("uuid");
                        ae.setKenyaemrEncounterUuid(encounterUuid);
                        ae.setResponseCode(String.valueOf(response.code()));
                    }else{
                        ae.setResponseCode("400");
                        //ae.setKenyaemrEncounterUuid();
                    }

                    System.out.println("Imefika Hapa na data "+ rescode);
                    amrsEncounterService.save(ae);
                }
            }
        }
    }
    public  static void newencounters( String url, String auth) throws JSONException, IOException {

        System.out.println("imeingia Hapa");

        JSONArray jsonObservations = new JSONArray();

        String puuid="a32d8e5e-fb75-419a-88aa-c2d5f37dd244";
        String pruuid="901218e9-dfc7-4afb-94cb-b32e551e4f76";
        String conceptuuid="1246AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        Boolean value=Boolean.TRUE;
        String edate="2024-11-18";

        JSONObject jsonObservation = new JSONObject();
        jsonObservation.put("person",puuid);//"60168b73-60f1-4044-9dc6-84fdcbc1962c");
        jsonObservation.put("concept",conceptuuid);///String.valueOf(conceptsetId));
        jsonObservation.put("value", value);

        JSONObject jsonVisit = new JSONObject();
        jsonVisit.put("patient",puuid);//"60168b73-60f1-4044-9dc6-84fdcbc1962c");
        jsonVisit.put("visitType","9865c08a-97a4-4571-b873-dd422583b3a7");
        jsonVisit.put("startDatetime",edate); //+"T06:08:25.000+0000"
        jsonVisit.put("stopDatetime",edate); //+"T06:09:25.000+0000"


        JSONArray jsonProviders = new JSONArray();
        JSONObject jsonProvider = new JSONObject();
        jsonProvider.put("provider", pruuid);
        jsonProvider.put("encounterRole", "a0b03050-c99b-11e0-9572-0800200c9a66");
        jsonProviders.put(jsonProvider);

        JSONObject jsonEncounter = new JSONObject();
        jsonEncounter.put("encounterProviders",jsonProviders);
        jsonEncounter.put("patient",puuid);//"60168b73-60f1-4044-9dc6-84fdcbc1962c");
        jsonEncounter.put("encounterDatetime",edate); //+"T06:09:00.000+0000"
        jsonEncounter.put("encounterType","465a92f2-baf8-42e9-9612-53064be868e8"); //euuid
        jsonEncounter.put("form","22c68f86-bbf0-49ba-b2d1-23fa7ccf0259"); //form
        jsonEncounter.put("location","c55535b8-b9f2-4a97-8c6c-4ea9496256df"); //luuid
        jsonEncounter.put("visit",jsonVisit);
        jsonEncounter.put("obs",jsonObservations);

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());
        //RequestBody body = RequestBody.create(mediaType, jsonEncounter.toString());
        Request request = new Request.Builder()
                .url(url + "user")
                .method("POST", body)
                .addHeader("Authorization", "Basic " + auth)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        String resBody = response.request().toString();
        int rescode = response.code();
        System.out.println("Imefika Hapa na data "+ rescode);
    }
}