package ampath.co.ke.amrs_kenyaemr.models;


import jakarta.persistence.*;

@Table(name = "amrsOrdersResults")
@Entity
public class AMRSOrdersResults {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String patientId;
    private String orderId;
    private String orderNumber;
    private String orderType;
    private String display;
    private String dateOrdered;
    private String sampleDrawn;
    private String sampleCollectionDate;
    private String finalOrderResults;
    private String responseCode;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSampleDrawn() {
        return sampleDrawn;
    }

    public void setSampleDrawn(String sampleDrawn) {
        this.sampleDrawn = sampleDrawn;
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getSampleCollectionDate() {
        return sampleCollectionDate;
    }

    public void setSampleCollectionDate(String sampleCollectionDate) {
        this.sampleCollectionDate = sampleCollectionDate;
    }

    public String getFinalOrderResults() {
        return finalOrderResults;
    }

    public void setFinalOrderResults(String finalOrderResults) {
        this.finalOrderResults = finalOrderResults;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
