package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

import ampath.co.ke.amrs_kenyaemr.models.*;
import ampath.co.ke.amrs_kenyaemr.service.*;
import ampath.co.ke.amrs_kenyaemr.tasks.IdentifierGenerator;
import ampath.co.ke.amrs_kenyaemr.tasks.Mappers;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class RegisterOpenMRSPayload {
    public static void users(AMRSUsers amrsUsers, AMRSUserServices amrsUserServices, String url, String auth ) throws JSONException, ParseException, IOException {
        //OpenMRS Payload
        //Addresses
        JSONArray jsonAddressesArray = new JSONArray();
        JSONObject jsonAddresses = new JSONObject();
        jsonAddresses.put("address1", amrsUsers.getAddress1());
        jsonAddresses.put("address2", amrsUsers.getAddress4());
        jsonAddresses.put("address3", amrsUsers.getAddress4());
        jsonAddresses.put("address4", amrsUsers.getAddress4());
        jsonAddresses.put("countyDistrict", amrsUsers.getCounty_district());
        jsonAddresses.put("stateProvince", amrsUsers.getCounty_district());
        jsonAddressesArray.put(jsonAddresses);
        //person
        JSONObject jsonPerson = new JSONObject();
        String startDateString = amrsUsers.getBirthdate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        jsonPerson.put("gender", amrsUsers.getGender());
        jsonPerson.put("birthdate", amrsUsers.getBirthdate());// sdf2.format(sdf.parse(startDateString)));
        String birthdateEstimatedc = amrsUsers.getBirthdate_estimate();
        String birthdateEstimated = "false";
        if (birthdateEstimatedc.equals("0")) {
            birthdateEstimated = "false";
        } else {
            birthdateEstimated = "true";
        }
        jsonPerson.put("birthdateEstimated", birthdateEstimated);
        String dead = amrsUsers.getDead();
        String deadcheck = "false";
        if (dead.equals("0")) {
            deadcheck = "false";
        } else {
            deadcheck = "true";
        }
        jsonPerson.put("dead", deadcheck);
        //Names
        JSONArray namearray = new JSONArray();
        JSONObject jsonNames = new JSONObject();
        jsonNames.put("givenName", amrsUsers.getGiven_name());
        jsonNames.put("familyName", amrsUsers.getFamily_name());
        jsonNames.put("middleName", amrsUsers.getMiddle_name());
        namearray.put(jsonNames);
        //Roles
        JSONArray rolesarray = new JSONArray();
        rolesarray.put("b1ad7622-9548-4c0e-a137-17ea4a2d892a");

        jsonPerson.put("names", namearray);
        jsonPerson.put("addresses", jsonAddressesArray);
        //user
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("username", amrsUsers.getUsername());
        jsonUser.put("password", "Admin123");
        jsonUser.put("person", jsonPerson);
        jsonUser.put("roles", rolesarray);

        // Basic Authentication
        //String auth = "admin" + ":" + "Admin123";
        //String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
        //String url = "http://192.168.100.48:8080/openmrs/ws/rest/v1/";


        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonUser.toString());
        String bodyString =body.toString();
        Request request = new Request.Builder()
                .url(url + "user")
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
        System.out.println("Response Payload " + jsonUser.toString());
        amrsUsers.setMigrated(1);
        amrsUsers.setResponse_code(String.valueOf(response.code()));
        amrsUserServices.save(amrsUsers);

    }

    public static void patient(AMRSPatients amrsPatients, AMRSPatientServices amrsPatientServices, AMRSIdentifiersService amrsIdentifiersService,AMRSPersonAtrributesService amrsPersonAtrributesService,AMRSPatientStatusService amrsPatientStatusService,String url,String auth) throws JSONException, ParseException, IOException {
        //OpenMRS Payload
        //identifier
        List<AMRSIdentifiers> identifiers = amrsIdentifiersService.getByPatientID(amrsPatients.getPersonId());
        JSONArray Identifierarray = new JSONArray();
        JSONArray PersonAttribute = new JSONArray();
        String OpenMRSID = IdentifierGenerator.generateIdentifier(url, auth);

        //Add OpenmrsID
        JSONObject jsonIdentifierOpenMRSID = new JSONObject();
        jsonIdentifierOpenMRSID.put("identifier", OpenMRSID);
        jsonIdentifierOpenMRSID.put("identifierType", "dfacd928-0370-4315-99d7-6ec1c9f7ae76");
        jsonIdentifierOpenMRSID.put("location", "3e6261cc-ad5e-4834-b85d-af8b42a133e4");
        jsonIdentifierOpenMRSID.put("preferred", "true");
        Identifierarray.put(jsonIdentifierOpenMRSID);
        AMRSIdentifiers ai = new AMRSIdentifiers();
        ai.setKenyaemr_uuid("dfacd928-0370-4315-99d7-6ec1c9f7ae76");
        ai.setPatientid(amrsPatients.getPersonId());
        ai.setPreferred("true");
        ai.setIdentifier(OpenMRSID);
        ai.setLocation("3e6261cc-ad5e-4834-b85d-af8b42a133e4");
        amrsIdentifiersService.save(ai);

        for (int x = 0; x < identifiers.size(); x++) {
            System.out.println("AMRS ID before Error " + amrsPatients.getPersonId() + " " + amrsPatients.getFamily_name() + " Type Failing" + identifiers.get(x).getIdentifier());

            if (identifiers.get(x).getUuid() == null) {

            } else {
                String identifyType = Mappers.identifers(identifiers.get(x).getUuid());
                if (identifyType.isEmpty() || identifiers.get(x).getUuid() == null) {

                } else {

                    JSONObject jsonIdentifier = new JSONObject();
                    jsonIdentifier.put("identifier", identifiers.get(x).getIdentifier());
                    jsonIdentifier.put("identifierType", identifyType);
                    jsonIdentifier.put("location", "3e6261cc-ad5e-4834-b85d-af8b42a133e4");
                    jsonIdentifier.put("preferred", "false");// identifiers.get(x).getPreferred());
                    Identifierarray.put(jsonIdentifier);
                }
            }
        }
        //Attributes
        List<AMRSPatientAttributes> amrsPatientAttributes = amrsPersonAtrributesService.getByPatientID(amrsPatients.getPersonId());
        if(amrsPatientAttributes.size()>0){
            for(int x=0;x<amrsPatientAttributes.size();x++){
               if(amrsPatientAttributes.get(x).getKenyaemrAttributeUuid() != null){
                   JSONObject jsonAttribute = new JSONObject();
                   jsonAttribute.put("attributeType", amrsPatientAttributes.get(x).getKenyaemrAttributeUuid());
                   jsonAttribute.put("value", amrsPatientAttributes.get(x).getPersonAttributeValue());
                   PersonAttribute.put(jsonAttribute);
               }

            }
        }
        //Start of Names
        JSONArray namearray = new JSONArray();
        JSONObject jsonNames = new JSONObject();
        jsonNames.put("givenName", amrsPatients.getGiven_name());
        jsonNames.put("familyName", amrsPatients.getFamily_name());
        jsonNames.put("middleName", amrsPatients.getMiddle_name());
        namearray.put(jsonNames);
        // End of dentifier
        JSONArray jsonAddressesArray = new JSONArray();
        JSONObject jsonAddresses = new JSONObject();
        //jsonAddresses.put("address1", amrsPatients.getCounty()); //County
        jsonAddresses.put("address2", amrsPatients.getLandmark()); //Land Mark
       // jsonAddresses.put("address3", amrsPatients.getAddress4());
        jsonAddresses.put("address6", amrsPatients.getAddress6()); //Location
        jsonAddresses.put("address5", amrsPatients.getAddress5()); //Sub Location
        jsonAddresses.put("address4", amrsPatients.getCounty()); //ward //amrsPatients.getAddress4()
        jsonAddresses.put("cityVillage", amrsPatients.getCityVillage()); //Village
        jsonAddresses.put("stateProvince", amrsPatients.getSubcounty()); //subcounty
        jsonAddresses.put("countyDistrict",amrsPatients.getCounty()); //county
        jsonAddresses.put("country", "Kenya"); //subcounty
        jsonAddressesArray.put(jsonAddresses);

        //person
        JSONObject jsonPerson = new JSONObject();
        jsonPerson.put("gender", amrsPatients.getGender());
        jsonPerson.put("birthdate", amrsPatients.getBirthdate());// sdf2.format(sdf.parse(startDateString)));
        String birthdateEstimatedc = amrsPatients.getBirthdate_estimated();
        String birthdateEstimated = "false";
        if (birthdateEstimatedc.equals("0")) {
            birthdateEstimated = "false";
        } else {
            birthdateEstimated = "true";
        }
        jsonPerson.put("birthdateEstimated", birthdateEstimated);
        String dead = amrsPatients.getDead();
        String deadcheck = "false";
        if (dead.equals("0")) {
            deadcheck = "false";
        } else {
            deadcheck = "true";
            jsonPerson.put("deathDate", amrsPatients.getDeathDate());
            jsonPerson.put("causeOfDeath", amrsPatients.getKenyaemrCauseOfDeadUuid());
            jsonPerson.put("deathdateEstimated", "false");

        }
        jsonPerson.put("dead", deadcheck);
        jsonPerson.put("names", namearray);
        jsonPerson.put("addresses", jsonAddressesArray);
        jsonPerson.put("attributes",PersonAttribute);

        JSONObject patientObject = new JSONObject();
        patientObject.put("identifiers", Identifierarray);
        patientObject.put("person", jsonPerson);

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, patientObject.toString());
        Request request = new Request.Builder()
                .url(url + "patient")
                .method("POST", body)
                .addHeader("Authorization", "Basic " + auth)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string(); // Get the response as a string
        System.out.println("Response ndo hii " + responseBody + " More message " + response.message() + " reponse code " + response.code());
        JSONObject jsonObject = new JSONObject(responseBody);

        if (response.code() ==201) {
            // Extract the person UUID
            String personUuid = jsonObject.getJSONObject("person").getString("uuid");

            // Print the person UUID
            System.out.println("Person UUID: " + personUuid);
            // Assuming the response is in JSON format and contains a 'uuid' field
            JSONObject jsonResponse = new JSONObject(responseBody); // Use a JSON parsing library
            String patientUuid = jsonResponse.optString("uuid", "UUID not found");


            // System.out.println("Response ndo hii " + jsonUser.toString());
            System.out.println("Response ndo hii " + response.request() + " More message " + response.message());
            amrsPatients.setMigrated(1);
            amrsPatients.setResponseCode(String.valueOf(response.code()));
            amrsPatients.setKenyaemrpatientUUID(personUuid);
            amrsPatientServices.save(amrsPatients);

        }
            System.out.println("identifers ndo hii " + patientObject.toString());
    }
    public static void relationship( AMRSPatientRelationshipService amrsPatientRelationshipService, String url, String auth ) throws JSONException, ParseException, IOException {
        List<AMRSPatientRelationship> amrsPatientRelationships = amrsPatientRelationshipService.findByResponseCodeIsNull();
        if(amrsPatientRelationships.size()>0){

            for(int x=0;x<amrsPatientRelationships.size();x++) {

                AMRSPatientRelationship amrsPatientRelationship = amrsPatientRelationships.get(x);

                if(amrsPatientRelationship.getKenyaemrpersonAUuid() !="" && amrsPatientRelationship.getKenyaemrpersonBUuid()!="" ) {

                    JSONObject patientObject = new JSONObject();
                    patientObject.put("personA", amrsPatientRelationship.getKenyaemrpersonAUuid());
                    patientObject.put("personB", amrsPatientRelationship.getKenyaemrpersonBUuid());
                    patientObject.put("relationshipType", amrsPatientRelationship.getRelationshipUuid());

                    System.out.println("Payload ndo hii" +patientObject.toString());

                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, patientObject.toString());
                    Request request = new Request.Builder()
                            .url(url + "relationship")
                            .method("POST", body)
                            .addHeader("Authorization", "Basic " + auth)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseBody = response.body().string(); // Get the response as a string
                    System.out.println("Response ndo hii " + responseBody + " More message " + response.message() + " reponse code " + response.code());
                    JSONObject jsonObject = new JSONObject(responseBody);

                    if (response.code() == 201) {
                        amrsPatientRelationship.setResponseCode("201");
                        amrsPatientRelationshipService.save(amrsPatientRelationship);

                    } else {
                        System.out.println("Error Iko Hapa");

                    }
                }
            }

        }
    }

    }
