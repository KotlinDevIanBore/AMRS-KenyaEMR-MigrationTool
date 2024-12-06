package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "amrs_green_card")
@Entity
public class AMRSGreenCard {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;


    private String patientId;

    private String formId;

    private String conceptId;

    private String encounterId;

    private String valueDatetime;

    private String valueCoded;


    private String valueNumeric;


    private String valueText;

    private  String responseCode;

    @Column(name = "amrs_uuid", length = 36)
    private String uuid;

    private String kenyaemrPatientUuid;

    private String kenyaEmrEncounterUuid;

    private String kenyaEmrConceptUuid;

    private String obsDateTime;

    private String kenyaEmrTcaUuid;

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

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public String getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(String encounterId) {
        this.encounterId = encounterId;
    }

    public String getValueDatetime() {
        return valueDatetime;
    }

    public void setValueDatetime(String valueDatetime) {
        this.valueDatetime = valueDatetime;
    }

    public String getValueCoded() {
        return valueCoded;
    }

    public void setValueCoded(String valueCoded) {
        this.valueCoded = valueCoded;
    }

    public String getValueNumeric() {
        return valueNumeric;
    }

    public void setValueNumeric(String valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getKenyaemrPatientUuid() {
        return kenyaemrPatientUuid;
    }

    public void setKenyaemrPatientUuid(String kenyaemrPatientUuid) {
        this.kenyaemrPatientUuid = kenyaemrPatientUuid;
    }

    public String getKenyaEmrEncounterUuid() {
        return kenyaEmrEncounterUuid;
    }

    public void setKenyaEmrEncounterUuid(String kenyaEmrEncounterUuid) {
        this.kenyaEmrEncounterUuid = kenyaEmrEncounterUuid;
    }

    public String getKenyaEmrConceptUuid() {
        return kenyaEmrConceptUuid;
    }

    public void setKenyaEmrConceptUuid(String kenyaEmrConceptUuid) {
        this.kenyaEmrConceptUuid = kenyaEmrConceptUuid;
    }

    public String getObsDateTime() {
        return obsDateTime;
    }

    public void setObsDateTime(String obsDateTime) {
        this.obsDateTime = obsDateTime;
    }

    public String getKenyaEmrTcaUuid() {
        return kenyaEmrTcaUuid;
    }

    public void setKenyaEmrTcaUuid(String kenyaEmrTcaUuid) {
        this.kenyaEmrTcaUuid = kenyaEmrTcaUuid;
    }
