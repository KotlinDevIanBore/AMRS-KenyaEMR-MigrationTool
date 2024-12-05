package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Table(name = "amrs_orders")
@Entity
public class AMRSOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Integer orderId;

    private Integer orderTypeId;

    private Integer conceptId;

    private String orderer;

    private Integer encounterId;

    private String instructions;

    private LocalDateTime dateActivated;


    private String orderReason;



    private LocalDateTime dateCreated;

    private Boolean voided;

    private Integer voidedBy;

    private LocalDateTime dateVoided;

    private String voidReason;

    private String patientId;

    private String responseCode;

    private String kenyaemrConceptUuid;

    private String orderType;

    private String orderAction;

    private String urgency;

    private String orderNumber;

    private Integer previousOrderId;

    private String kenyaemrPatientUuid;

    @Column(name = "migration_uuid", length = 36)
    private String migrationUuid;


    private String kenyaemrOrderUuid;


    private Integer kenyaemrOrderId;

    private String kenyaEmrEncounterUuid;


    private String display;

    private String sampleDrawn;

    private String sampleCollectionDate;


    private String finalOrderResult;

    private String dateOrdered;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Integer getConceptId() {
        return conceptId;
    }

    public void setConceptId(Integer conceptId) {
        this.conceptId = conceptId;
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public Integer getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public LocalDateTime getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(LocalDateTime dateActivated) {
        this.dateActivated = dateActivated;
    }

    public String getOrderReason() {
        return orderReason;
    }

    public void setOrderReason(String orderReason) {
        this.orderReason = orderReason;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    public Integer getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Integer voidedBy) {
        this.voidedBy = voidedBy;
    }

    public LocalDateTime getDateVoided() {
        return dateVoided;
    }

    public void setDateVoided(LocalDateTime dateVoided) {
        this.dateVoided = dateVoided;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getKenyaemrConceptUuid() {
        return kenyaemrConceptUuid;
    }

    public void setKenyaemrConceptUuid(String kenyaemrConceptUuid) {
        this.kenyaemrConceptUuid = kenyaemrConceptUuid;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getPreviousOrderId() {
        return previousOrderId;
    }

    public void setPreviousOrderId(Integer previousOrderId) {
        this.previousOrderId = previousOrderId;
    }

    public String getKenyaemrPatientUuid() {
        return kenyaemrPatientUuid;
    }

    public void setKenyaemrPatientUuid(String kenyaemrPatientUuid) {
        this.kenyaemrPatientUuid = kenyaemrPatientUuid;
    }

    public String getMigrationUuid() {
        return migrationUuid;
    }

    public void setMigrationUuid(String migrationUuid) {
        this.migrationUuid = migrationUuid;
    }

    public String getKenyaemrOrderUuid() {
        return kenyaemrOrderUuid;
    }

    public void setKenyaemrOrderUuid(String kenyaemrOrderUuid) {
        this.kenyaemrOrderUuid = kenyaemrOrderUuid;
    }

    public Integer getKenyaemrOrderId() {
        return kenyaemrOrderId;
    }

    public void setKenyaemrOrderId(Integer kenyaemrOrderId) {
        this.kenyaemrOrderId = kenyaemrOrderId;
    }

    public String getKenyaEmrEncounterUuid() {
        return kenyaEmrEncounterUuid;
    }

    public void setKenyaEmrEncounterUuid(String kenyaEmrEncounterUuid) {
        this.kenyaEmrEncounterUuid = kenyaEmrEncounterUuid;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getSampleDrawn() {
        return sampleDrawn;
    }

    public void setSampleDrawn(String sampleDrawn) {
        this.sampleDrawn = sampleDrawn;
    }

    public String getSampleCollectionDate() {
        return sampleCollectionDate;
    }

    public void setSampleCollectionDate(String sampleCollectionDate) {
        this.sampleCollectionDate = sampleCollectionDate;
    }

    public String getFinalOrderResult() {
        return finalOrderResult;
    }

    public void setFinalOrderResult(String finalOrderResult) {
        this.finalOrderResult = finalOrderResult;
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }
}

