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
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_type_id")
    private Integer orderTypeId;

    @Column(name = "concept_id")
    private Integer conceptId;

    @Column(name = "orderer")
    private Integer orderer;

    @Column(name = "encounter_id")
    private Integer encounterId;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "date_activated")
    private LocalDateTime dateActivated;

    @Column(name = "auto_expire_date")
    private LocalDateTime autoExpireDate;

    @Column(name = "date_stopped")
    private LocalDateTime dateStopped;

    @Column(name = "order_reason")
    private Integer orderReason;

    @Column(name = "order_reason_non_coded")
    private String orderReasonNonCoded;

    @Column(name = "creator")
    private Integer creator;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "voided")
    private Boolean voided;

    @Column(name = "voided_by")
    private Integer voidedBy;

    @Column(name = "date_voided")
    private LocalDateTime dateVoided;

    @Column(name = "void_reason")
    private String voidReason;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "accession_number")
    private String accessionNumber;

    @Column(name = "uuid", length = 36)
    private String uuid;

    @Column(name = "urgency")
    private String urgency;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "previous_order_id")
    private Integer previousOrderId;

    @Column(name = "order_action")
    private String orderAction;

    @Column(name = "comment_to_fulfiller")
    private String commentToFulfiller;

    @Column(name = "care_setting")
    private Integer careSetting;

    @Column(name = "scheduled_date")
    private LocalDateTime scheduledDate;

    @Column(name = "order_group_id")
    private Integer orderGroupId;

    @Column(name = "sort_weight")
    private Double sortWeight;

    @Column(name = "fulfiller_comment")
    private String fulfillerComment;

    @Column(name = "fulfiller_status")
    private String fulfillerStatus;

    @Column(name = "migration_uuid", length = 36)
    private String migrationUuid;

    @Column(name = "kenyaemr_order_uuid")
    private String kenyaemrOrderUuid;

    @Column(name = "kenyaemr_order_id")
    private Integer kenyaemrOrderId;

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

    public Integer getOrderer() {
        return orderer;
    }

    public void setOrderer(Integer orderer) {
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

    public LocalDateTime getAutoExpireDate() {
        return autoExpireDate;
    }

    public void setAutoExpireDate(LocalDateTime autoExpireDate) {
        this.autoExpireDate = autoExpireDate;
    }

    public LocalDateTime getDateStopped() {
        return dateStopped;
    }

    public void setDateStopped(LocalDateTime dateStopped) {
        this.dateStopped = dateStopped;
    }

    public Integer getOrderReason() {
        return orderReason;
    }

    public void setOrderReason(Integer orderReason) {
        this.orderReason = orderReason;
    }

    public String getOrderReasonNonCoded() {
        return orderReasonNonCoded;
    }

    public void setOrderReasonNonCoded(String orderReasonNonCoded) {
        this.orderReasonNonCoded = orderReasonNonCoded;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public String getCommentToFulfiller() {
        return commentToFulfiller;
    }

    public void setCommentToFulfiller(String commentToFulfiller) {
        this.commentToFulfiller = commentToFulfiller;
    }

    public Integer getCareSetting() {
        return careSetting;
    }

    public void setCareSetting(Integer careSetting) {
        this.careSetting = careSetting;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Integer getOrderGroupId() {
        return orderGroupId;
    }

    public void setOrderGroupId(Integer orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    public Double getSortWeight() {
        return sortWeight;
    }

    public void setSortWeight(Double sortWeight) {
        this.sortWeight = sortWeight;
    }

    public String getFulfillerComment() {
        return fulfillerComment;
    }

    public void setFulfillerComment(String fulfillerComment) {
        this.fulfillerComment = fulfillerComment;
    }

    public String getFulfillerStatus() {
        return fulfillerStatus;
    }

    public void setFulfillerStatus(String fulfillerStatus) {
        this.fulfillerStatus = fulfillerStatus;
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
}

