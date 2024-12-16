package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;


@Table(name = "art_refill")
@Entity
public class AMRSArtRefill {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer Id;
    private String locationUuid;
    private String patientId;
    private String encounterId;
    private String encounterDateTime;
    private String formId;
    private String encounterType;
    private String conceptId;
    private String conceptName;
    private String conceptValue;
    private String obsDateTime;
    private String value;
    private String valueDataType;
    private String dataTypeId;
    private String encounterName;
    private String Category;
    private String kenyaemrValue;
    private String kenyaEmrVisitUuid;
    private String kenyaEmrPatientUuid;
    private String kenyaEmrEncounterUuid;
    private String kenyaEmrConceptUuid;
    private String responseCode;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getLocationUuid() {
        return locationUuid;
    }

    public void setLocationUuid(String locationUuid) {
        this.locationUuid = locationUuid;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(String encounterID) {
        this.encounterId = encounterID;
    }

    public String getEncounterDateTime() {
        return encounterDateTime;
    }

    public void setEncounterDateTime(String encounterDateTime) {
        this.encounterDateTime = encounterDateTime;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(String encounterType) {
        this.encounterType = encounterType;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public String getConceptValue() {
        return conceptValue;
    }

    public void setConceptValue(String conceptValue) {
        this.conceptValue = conceptValue;
    }

    public String getObsDateTime() {
        return obsDateTime;
    }

    public void setObsDateTime(String obsDateTime) {
        this.obsDateTime = obsDateTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueDataType() {
        return valueDataType;
    }

    public void setValueDataType(String valueDataType) {
        this.valueDataType = valueDataType;
    }

    public String getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(String dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getEncounterName() {
        return encounterName;
    }

    public void setEncounterName(String encounterName) {
        this.encounterName = encounterName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }


    public String getKenyaemrValue() {
        return kenyaemrValue;
    }

    public void setKenyaemrValue(String kenyaemrValue) {
        this.kenyaemrValue = kenyaemrValue;
    }

    public String getKenyaEmrVisitUuid() {
        return kenyaEmrVisitUuid;
    }

    public void setKenyaEmrVisitUuid(String kenyaemrVisitUuid) {
        this.kenyaEmrVisitUuid = kenyaemrVisitUuid;
    }

    public String getKenyaEmrPatientUuid() {
        return kenyaEmrPatientUuid;
    }

    public void setKenyaEmrPatientUuid(String kenyaemrPatientUuid) {
        this.kenyaEmrPatientUuid = kenyaemrPatientUuid;
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

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
