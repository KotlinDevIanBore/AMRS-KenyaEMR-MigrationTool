package ampath.co.ke.amrs_kenyaemr.models;


import jakarta.persistence.*;

@Table(name = "patientStatus")
@Entity
public class AMRSPatientStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)

    private Integer Id;
    private String personId;
    private String personAttributeTypeId;
    private String kenyaEmrConcept;
    private String kenyaEmrConceptUuid;
    private String name;
    private String valueName;
    private String value;
    private String kenyaEmrValueUuid;

    private String kenyaPatientUuid;

    private String obsDateTime;
    private String responseCode;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonAttributeTypeId() {
        return personAttributeTypeId;
    }

    public void setPersonAttributeTypeId(String personAttributeTypeId) {
        this.personAttributeTypeId = personAttributeTypeId;
    }

    public String getKenyaEmrConcept() {
        return kenyaEmrConcept;
    }

    public void setKenyaEmrConcept(String kenyaEmrConcept) {
        this.kenyaEmrConcept = kenyaEmrConcept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getKenyaEmrConceptUuid() {
        return kenyaEmrConceptUuid;
    }

    public void setKenyaEmrConceptUuid(String kenyaEmrConceptUuid) {
        this.kenyaEmrConceptUuid = kenyaEmrConceptUuid;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getKenyaEmrValueUuid() {
        return kenyaEmrValueUuid;
    }

    public void setKenyaEmrValueUuid(String kenyaEmrValueUuid) {
        this.kenyaEmrValueUuid = kenyaEmrValueUuid;
    }

    public String getKenyaPatientUuid() {
        return kenyaPatientUuid;
    }

    public void setKenyaPatientUuid(String kenyaPatientUuid) {
        this.kenyaPatientUuid = kenyaPatientUuid;
    }

    public String getObsDateTime() {
        return obsDateTime;
    }

    public void setObsDateTime(String obsDateTime) {
        this.obsDateTime = obsDateTime;
    }
}
