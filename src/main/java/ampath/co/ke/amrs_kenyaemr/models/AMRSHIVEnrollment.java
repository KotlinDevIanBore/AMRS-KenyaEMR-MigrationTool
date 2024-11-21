package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "hivenrollment")
@Entity
public class AMRSHIVEnrollment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer Id;
    private String locationUuid;
    private String patientId;
    private String encounterID;
    private String encounterDateTime;
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

    private String kenyaemrEncounterUuid;
    private String kenyaemrConceptUuid;
    private String kenyaemrValue;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    private String provider;
    private int responseCode;

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

    public String getEncounterID() {
        return encounterID;
    }
    public String getConceptValue() {
        return conceptValue;
    }

    public void setConceptValue(String conceptValue) {
        this.conceptValue = conceptValue;
    }
    public void setEncounterID(String encounterID) {
        this.encounterID = encounterID;
    }

    public String getEncounterDateTime() {
        return encounterDateTime;
    }

    public void setEncounterDateTime(String encounterDateTime) {
        this.encounterDateTime = encounterDateTime;
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

    public String getKenyaemrEncounterUuid() {
        return kenyaemrEncounterUuid;
    }

    public void setKenyaemrEncounterUuid(String kenyaemrEncounterUuid) {
        this.kenyaemrEncounterUuid = kenyaemrEncounterUuid;
    }

    public String getKenyaemrConceptUuid() {
        return kenyaemrConceptUuid;
    }

    public void setKenyaemrConceptUuid(String kenyaemrConceptUuid) {
        this.kenyaemrConceptUuid = kenyaemrConceptUuid;
    }

    public String getKenyaemrValue() {
        return kenyaemrValue;
    }

    public void setKenyaemrValue(String kenyaemrValue) {
        this.kenyaemrValue = kenyaemrValue;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
