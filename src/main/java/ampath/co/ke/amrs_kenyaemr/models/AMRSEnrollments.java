package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "enrollments")
@Entity
public class AMRSEnrollments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer Id;
    private String UUID;
    private String personId;
    private String formID;
    private String EncounterType;
    private String AMRSEncounterType;
    private String EnrollmentDate;
    private String TransferIn;
    private String TransferInDate;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public String getEncounterType() {
        return EncounterType;
    }

    public void setEncounterType(String encounterType) {
        EncounterType = encounterType;
    }

    public String getAMRSEncounterType() {
        return AMRSEncounterType;
    }

    public void setAMRSEncounterType(String AMRSEncounterType) {
        this.AMRSEncounterType = AMRSEncounterType;
    }

    public String getEnrollmentDate() {
        return EnrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        EnrollmentDate = enrollmentDate;
    }

    public String getTransferIn() {
        return TransferIn;
    }

    public void setTransferIn(String transferIn) {
        TransferIn = transferIn;
    }

    public String getTransferInDate() {
        return TransferInDate;
    }

    public void setTransferInDate(String transferInDate) {
        TransferInDate = transferInDate;
    }
}
