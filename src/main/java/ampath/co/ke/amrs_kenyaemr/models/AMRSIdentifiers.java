package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "identifier")
@Entity
public class AMRSIdentifiers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name="patient_id")
    private String patientid;

    @Column(name="uuid")
    private String uuid;

    @Column(name="kenyaemr_uuid")
    private String kenyaemr_uuid;
    @Column(name="identifier")
    private String identifier;
    @Column(name="identifier_type")
    private String identifierType;
    @Column(name = "preferred")
    private String preferred;
    @Column(name = "location")
    private String location;

    @Column(name = "voided")
    private String voided;

    @Column(name = "parent_location_uuid")
    private String parentlocationuuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getKenyaemr_uuid() {
        return kenyaemr_uuid;
    }

    public void setKenyaemr_uuid(String kenyaemr_uuid) {
        this.kenyaemr_uuid = kenyaemr_uuid;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPreferred() {
        return preferred;
    }

    public void setPreferred(String preferred) {
        this.preferred = preferred;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParentlocationuuid() {
        return parentlocationuuid;
    }

    public void setParentlocationuuid(String parentlocationuuid) {
        this.parentlocationuuid = parentlocationuuid;
    }

    public String getVoided() {
        return voided;
    }

    public void setVoided(String voided) {
        this.voided = voided;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }
}
