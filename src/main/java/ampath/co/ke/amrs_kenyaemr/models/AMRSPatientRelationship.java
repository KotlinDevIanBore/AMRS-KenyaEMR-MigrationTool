package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;


@Entity
public class AMRSPatientRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    private String personA;
    private String KenyaemrpersonAUuid;
    private String personB;
    private String KenyaemrpersonBUuid;

    private String relationshipType;
    private String aistob;
    private String bistoa;
    private String relationshipUuid;

    private  String responseCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonA() {
        return personA;
    }

    public void setPersonA(String personA) {
        this.personA = personA;
    }

    public String getKenyaemrpersonAUuid() {
        return KenyaemrpersonAUuid;
    }

    public void setKenyaemrpersonAUuid(String kenyaemrpersonAUuid) {
        KenyaemrpersonAUuid = kenyaemrpersonAUuid;
    }

    public String getPersonB() {
        return personB;
    }

    public void setPersonB(String personB) {
        this.personB = personB;
    }

    public String getKenyaemrpersonBUuid() {
        return KenyaemrpersonBUuid;
    }

    public void setKenyaemrpersonBUuid(String kenyaemrpersonBUuid) {
        KenyaemrpersonBUuid = kenyaemrpersonBUuid;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getAistob() {
        return aistob;
    }

    public void setAistob(String aistob) {
        this.aistob = aistob;
    }

    public String getBistoa() {
        return bistoa;
    }

    public void setBistoa(String bistoa) {
        this.bistoa = bistoa;
    }

    public String getRelationshipUuid() {
        return relationshipUuid;
    }

    public void setRelationshipUuid(String relationshipUuid) {
        this.relationshipUuid = relationshipUuid;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
