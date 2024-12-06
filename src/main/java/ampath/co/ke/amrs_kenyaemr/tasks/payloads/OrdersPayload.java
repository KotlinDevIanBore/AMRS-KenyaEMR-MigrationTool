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
    public static void orders(AMRSOrderService amrsOrderService, AMRSPatientServices amrsPatientServices, String url, String auth) throws JSONException, IOException {
        List<AMRSOrders> amrsOrders = amrsOrderService.findByResponseCodeIsNull();
       if(!amrsOrders.isEmpty()) {

            System.out.println("Size ndo hii "+ amrsOrders.size());

            for (int x = 0; x < amrsOrders.size(); x++) {
                AMRSOrders amrsOrders1 = amrsOrders.get(x);
                if(amrsOrders1.getResponseCode() == null) {

                    List<AMRSPatients> patientsList = amrsPatientServices.getByPatientID(amrsOrders1.getPatientId());
                    System.out.println(" Patient Iko  "+ patientsList.size());
                    if (!patientsList.isEmpty()) {
                        String pid = patientsList.get(0).getKenyaemrpatientUUID();
                        Integer kenyaemrOrderId = amrsOrders.get(x).getKenyaemrOrderId();
                        String orderNumber = amrsOrders.get(x).getOrderNumber();
                        Integer orderType = amrsOrders.get(x).getOrderTypeId();
                        String urgency = amrsOrders.get(x).getUrgency();
                        String orderReason = amrsOrders.get(x).getOrderReason();
                        String concept_uuid=amrsOrders.get(x).getKenyaemrConceptUuid();

                        String result = amrsOrders.get(x).getFinalOrderResult();
                        String resultsDate = amrsOrders.get(x).getDateOrdered();

                        String orderAction = amrsOrders.get(x).getOrderAction();
                        if(concept_uuid !=null) {

                            JSONObject jsonOrder = new JSONObject();

                            jsonOrder.put("urgency", urgency);
                            jsonOrder.put("orderer", "ae01b8ff-a4cc-4012-bcf7-72359e852e14");
                            jsonOrder.put("orderReason", orderReason);
                            jsonOrder.put("careSetting", "OUTPATIENT");
                            jsonOrder.put("patient", amrsOrders.get(x).getKenyaemrPatientUuid());
                            jsonOrder.put("concept", concept_uuid);
                            jsonOrder.put("encounter", amrsOrders.get(x).getKenyaEmrEncounterUuid());
                            jsonOrder.put("action", orderAction);
                            jsonOrder.put("dateActivated", resultsDate);
                            jsonOrder.put("type", "testorder");
//                            jsonOrder.put("dateStopped",resultsDate);

                            System.out.println("---------------------------------------------");
                            System.out.println(jsonOrder.toString());
                            System.out.println("----------------------------------------------");

                            if (amrsOrders.get(0).getKenyaEmrEncounterUuid() != null) {
                                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXX---------------" + amrsOrders.get(0).getKenyaemrPatientUuid());
                            }else{
                                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXX- Missing Endcounter--------------" + amrsOrders.get(0).getKenyaemrPatientUuid());

                            }

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
//                        System.out.println("Response ndo hii " + responseBody + " More message " + response.message() + " reponse code " + response.code());
                            JSONObject jsonObject = new JSONObject(responseBody);
                            System.out.println("Response ndo hii " + response);
//                        System.out.println("Response Payload " + jsonOrder.toString());
                            if (response.code() == 201) {
                                String orderUuid = jsonObject.getString("uuid");

                                // update results

                                JSONObject orderResultsObj = new JSONObject();
                                orderResultsObj.put("person", amrsOrders.get(x).getKenyaemrPatientUuid());
                                orderResultsObj.put("concept", concept_uuid);
                                orderResultsObj.put("encounter", amrsOrders.get(x).getKenyaEmrEncounterUuid());
                                orderResultsObj.put("obsDatetime", resultsDate);
                                orderResultsObj.put("value", result);
                                orderResultsObj.put("order",orderUuid);


                                if(ordersResults(orderResultsObj,url,auth)){
                                    amrsOrders1.setKenyaemrOrderUuid(orderUuid);
                                    amrsOrders1.setResponseCode(String.valueOf(response.code()));
                                    amrsOrderService.save(amrsOrders1);
                                }

                            }else{
                                System.out.println("Response ndo hii " + response.code());
                            }
                        }
                    } else {
                        System.out.println("Order not saved");
                    }

                }
            }
        }
    }

    public static boolean ordersResults(JSONObject orderResultObj, String url, String auth) throws JSONException, IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, orderResultObj.toString());
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
            return true;
        } else {
            System.out.println("failed saving observations " + response.code());
            return false;
        }

    }



}
