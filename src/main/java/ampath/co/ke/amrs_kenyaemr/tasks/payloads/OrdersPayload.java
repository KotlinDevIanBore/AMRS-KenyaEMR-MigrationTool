package ampath.co.ke.amrs_kenyaemr.tasks.payloads;

import ampath.co.ke.amrs_kenyaemr.models.AMRSOrders;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.service.AMRSOrderService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import okhttp3.*;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class OrdersPayload {
    public static void orders(AMRSOrderService amrsOrderService, AMRSPatientServices amrsPatientServices, String auth, String url) throws JSONException, IOException {
        List<AMRSOrders> amrsOrders = amrsOrderService.getAll();
        if(amrsOrders.size() > 0) {
            for (int x = 0; x < amrsOrders.size(); x++) {
                AMRSOrders amrsOrders1 = amrsOrders.get(x);
                if(amrsOrders1.getKenyaemrOrderId() == null) {
                    List<AMRSPatients> patientsList = amrsPatientServices.getByPatientID(amrsOrders1.getPatientId());
                    if (patientsList.size() > 0) {
                        String pid = patientsList.get(0).getKenyaemrpatientUUID();
                        Integer kenyaemrOrderId = amrsOrders.get(x).getKenyaemrOrderId();
                        String orderNumber = amrsOrders.get(x).getOrderNumber();
                        Integer orderType = amrsOrders.get(x).getOrderTypeId();
                        String urgency = amrsOrders.get(x).getUrgency();
                        String orderer = amrsOrders.get(x).getOrderer();
                        Integer amrs_uuid = Integer.valueOf(amrsOrders.get(x).getUuid());
                        String careSettings = amrsOrders.get(x).getCareSetting();
                        String orderReason = amrsOrders.get(x).getOrderReason();

                        JSONObject jsonOrder = new JSONObject();
                        jsonOrder.put("patient", pid);
                        jsonOrder.put("kenyaemrOrderId", kenyaemrOrderId);
                        jsonOrder.put("orderNumber", orderNumber);
                        jsonOrder.put("orderType", orderType);
                        jsonOrder.put("urgency", urgency);
                        jsonOrder.put("orderer", orderer);
                        jsonOrder.put("orderReason", orderReason);
                        jsonOrder.put("amrs_uuid", amrs_uuid);
                        jsonOrder.put("careSettings", "OUTPATIENT");

                        OkHttpClient client = new OkHttpClient();
                        MediaType mediaType = MediaType.parse("application/json");
                        RequestBody body = RequestBody.create(mediaType, jsonOrder.toString());
                        String bodyString = body.toString();
                        Request request = new Request.Builder()
                                .url(url + "order")
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
                        System.out.println("Response Payload " + jsonOrder.toString());
                        if(response.code()==201) {
                            Integer orderUuid = Integer.valueOf(jsonObject.getString("uuid"));
                            amrsOrders1.setKenyaemrOrderId(orderUuid);
                            amrsOrders1.setResponseCode(String.valueOf(response.code()));
                            amrsOrderService.save(amrsOrders1);
                        }
                    } else {
                        System.out.println("Order already exists");
                    }

                }
            }
        }
    }
}
