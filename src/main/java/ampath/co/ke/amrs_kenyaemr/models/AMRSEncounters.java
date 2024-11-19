package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "encounters")
@Entity
public class AMRSEncounters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name="patient_id")
    private String patientId;
    @Column(name="uuid")
    private String UUID;
    private String encounterID;
    private String encounterDatetime;
    private String encounterType;
    private String encounterName;
    private String DataType;
    private String conceptId;
    private String obsDatetime;
    private String valueType;
    private String value;
    @Column(name="kenyaemr_value")
    private String kenyaemrvalue;
    @Column(name="kenyaemr_location_uuid")
    private String kenyaemrlocationuuid;
    @Column(name="kenyaemr_concept_uuid")
    private String kenyaemrconceptuuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getEncounterID() {
        return encounterID;
    }

    public void setEncounterID(String encounterID) {
        this.encounterID = encounterID;
    }

    public String getEncounterDatetime() {
        return encounterDatetime;
    }

    public void setEncounterDatetime(String encounterDatetime) {
        this.encounterDatetime = encounterDatetime;
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

    public String getObsDatetime() {
        return obsDatetime;
    }

    public void setObsDatetime(String obsDatetime) {
        this.obsDatetime = obsDatetime;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getKenyaemrvalue() {
        return kenyaemrvalue;
    }

    public void setKenyaemrvalue(String kenyaemrvalue) {
        this.kenyaemrvalue = kenyaemrvalue;
    }

    public String getKenyaemrlocationuuid() {
        return kenyaemrlocationuuid;
    }

    public void setKenyaemrlocationuuid(String kenyaemrlocationuuid) {
        this.kenyaemrlocationuuid = kenyaemrlocationuuid;
    }

    public String getKenyaemrconceptuuid() {
        return kenyaemrconceptuuid;
    }

    public void setKenyaemrconceptuuid(String kenyaemrconceptuuid) {
        this.kenyaemrconceptuuid = kenyaemrconceptuuid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEncounterName() {
        return encounterName;
    }

    public void setEncounterName(String encounterName) {
        this.encounterName = encounterName;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }
}

