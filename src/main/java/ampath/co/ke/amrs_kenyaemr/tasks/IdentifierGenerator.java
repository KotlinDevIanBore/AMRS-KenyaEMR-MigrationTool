package ampath.co.ke.amrs_kenyaemr.tasks;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;
import java.util.Random;

public class IdentifierGenerator {

    public static String generateIdentifier(String url, String auth) throws IOException {
        String identifer="";

        System.out.println("OpenMRS ID "+ url+ "idgen/nextIdentifier?source=1");
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(url + "idgen/nextIdentifier?source=1")
                .addHeader("Authorization", "Basic " + auth)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        // Create a Gson instance
        String responseBody = response.body().string();
        Gson gson = new Gson();
        System.out.println("Identifier Value Response: " + response.toString());
        System.out.println("Response ndo hii " + responseBody);

        // Parse the JSON response

        JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);

        // Access data from jsonObject
        System.out.println("Identifier Value: " + jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject().get("identifierValue").getAsString());

        identifer  =  jsonObject.getAsJsonArray("results")
                .get(0).getAsJsonObject().get("identifierValue").getAsString();

       /* JsonObject jsonObject = response.body().string(), JsonObject.class);
        JsonArray resultsArray = jsonObject.getAsJsonArray("results");

        // Iterate over the results
        for (int i = 0; i < resultsArray.size(); i++) {
            JsonObject resultObject = resultsArray.get(i).getAsJsonObject();
            String identifierValue = resultObject.get("identifierValue").getAsString();
            System.out.println("Identifier Value: " + identifierValue);
            identifer = identifierValue;
        }
        */

        return identifer;
    }
}
