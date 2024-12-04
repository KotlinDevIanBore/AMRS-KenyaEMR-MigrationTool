package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncounters;
import ampath.co.ke.amrs_kenyaemr.models.AMRSEnrollments;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.models.AMRSTriage;
import ampath.co.ke.amrs_kenyaemr.service.AMRSEncounterService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSEnrollmentService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class NewEnrollmentPayload {
  public  static void enrollments(AMRSEnrollmentService amrsEnrollmentService, AMRSEncounterService amrsEncounterService, String url, String auth) throws JSONException, IOException {

    List<AMRSEnrollments> amrsEnrollmentsList = amrsEnrollmentService.getAll();
    if (amrsEnrollmentsList.size() > 0) {
      JSONArray jsonObservations = new JSONArray();
      for (int x = 0; x < amrsEnrollmentsList.size(); x++) {
        AMRSEnrollments at = amrsEnrollmentsList.get(x);
        // List<AMRSEnrollments> amrsEnrollments = amrsEnrollmentService.getByPatientID(at.getPatientId().toString());
        List<AMRSEncounters> amrsEncounters = amrsEncounterService.findByEncounterId(at.getEncounterId());
        // System.out.println("Patient ID " + amrsPatients.get(0).getPatientId() + " Encounter Id " + at.getEncounterId());

        if(amrsEncounters.size() > 0){
          JSONObject jsonEncounter = new JSONObject();
          jsonEncounter.put("form","e4b506c1-7379-42b6-a374-284469cba8da");

          JSONObject jsonObservationPatientType = new JSONObject();
          String conceptvalue = null;

          switch (at.getPatientType()) {
            case "164144": // New client
              conceptvalue = "164144AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
              break;
            case "160563": // Transfer in
              conceptvalue = "160563AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
              break;
            case "164931": // Transit patient
              conceptvalue = "4bd29eed-e486-426d-a2b6-7e5bb75319f6";
              break;
            case "159833": // Re-enrollment(Re-activation)
              conceptvalue = "159833AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
              break;
            default:
              throw new IllegalArgumentException("Unknown patient type: " + at.getPatientType());
          }
          if (conceptvalue != null) {
            jsonObservationPatientType.put("concept", "423c034e-14ac-4243-ae75-80d1daddce55"); // patient type
            jsonObservationPatientType.put("value", conceptvalue);
            jsonObservations.put(jsonObservationPatientType);
          }

          JSONObject jsonObservationDateStartedArtAtTransferringFacility = new JSONObject();
          jsonObservationDateStartedArtAtTransferringFacility.put("concept","159599AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
          jsonObservationDateStartedArtAtTransferringFacility.put("value", at.getDateStartedArtAtTransferringFacility());
          jsonObservations.put(jsonObservationDateStartedArtAtTransferringFacility);

          JSONObject jsonObservationDateFirstEnrolled = new JSONObject();
          jsonObservationDateFirstEnrolled.put("concept","160555AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
          jsonObservationDateFirstEnrolled.put("value", at.getDateFirstEnrolledInCare());
          jsonObservations.put(jsonObservationDateFirstEnrolled);

          jsonEncounter.put("obs",jsonObservations);

          OkHttpClient client = new OkHttpClient();
          MediaType mediaType = MediaType.parse("application/json");
          okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());
          //RequestBody body = RequestBody.create(mediaType, jsonEncounter.toString());
          Request request = new Request.Builder()
            .url(url + "encounter/"+amrsEncounters.get(0).getKenyaemrEncounterUuid())
            .method("POST", body)
            .addHeader("Authorization", "Basic " + auth)
            .addHeader("Content-Type", "application/json")
            .build();
           Response response = client.newCall(request).execute();
           String responseBody = response.body().string(); // Get the response as a string
           System.out.println("Response " + responseBody + " More message " + response.message());

          //amrsEnrollmentService.save(at);

          if(amrsEncounters.size()>0) {
             System.out.println("Encounter ID " + amrsEncounters.get(0).getEncounterId() + " Encounter Date " + at.getEncounterDatetime());
          }

        }

      }
    }


//        JSONObject jsonEncounter = new JSONObject();
//    // where you create the openmrs consumable payload
//
//    OkHttpClient client = new OkHttpClient();
//    MediaType mediaType = MediaType.parse("application/json");
//    okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, jsonEncounter.toString());
//    //RequestBody body = RequestBody.create(mediaType, jsonEncounter.toString());
//    Request request = new Request.Builder()
//      .url(url + "encounter")
//      .method("POST", body)
//      .addHeader("Authorization", "Basic " + auth)
//      .addHeader("Content-Type", "application/json")
//      .build();
//    Response response = client.newCall(request).execute();
//
//    String resBody = response.request().toString();
//    int rescode = response.code();
//    System.out.println("Imefika Hapa na data "+ rescode);
  }

}
