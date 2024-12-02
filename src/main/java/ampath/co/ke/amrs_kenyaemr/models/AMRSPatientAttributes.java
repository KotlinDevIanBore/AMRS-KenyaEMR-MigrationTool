package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "patient_attributes")
@Entity
public class AMRSPatientAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name="patient_id")
    private String patientId;
    @Column(name="person_attribute_type_id")
    private String personAttributeTypeId;
    @Column(name="person_attribute_type_name")
    private String personAttributeName;
    @Column(name="person_attribute_value")
    private String personAttributeValue;
    @Column(name="kenyaemr_attribute_uuid")
    private String kenyaemrAttributeUuid;
    @Column(name="kenyaemr_attribute_value")
    private String kenyaemrAttributeValue;

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

    public String getPersonAttributeTypeId() {
        return personAttributeTypeId;
    }

    public void setPersonAttributeTypeId(String personAttributeTypeId) {
        this.personAttributeTypeId = personAttributeTypeId;
    }

    public String getPersonAttributeName() {
        return personAttributeName;
    }

    public void setPersonAttributeName(String personAttributeName) {
        this.personAttributeName = personAttributeName;
    }

    public String getPersonAttributeValue() {
        return personAttributeValue;
    }

    public void setPersonAttributeValue(String personAttributeValue) {
        this.personAttributeValue = personAttributeValue;
    }

    public String getKenyaemrAttributeUuid() {
        return kenyaemrAttributeUuid;
    }

    public void setKenyaemrAttributeUuid(String kenyaemrAttributeUuid) {
        this.kenyaemrAttributeUuid = kenyaemrAttributeUuid;
    }

    public String getKenyaemrAttributeValue() {
        return kenyaemrAttributeValue;
    }

    public void setKenyaemrAttributeValue(String kenyaemrAttributeValue) {
        this.kenyaemrAttributeValue = kenyaemrAttributeValue;
    }
}
