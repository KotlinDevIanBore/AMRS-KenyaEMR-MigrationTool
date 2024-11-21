package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "programs")
@Entity
public class AMRSPrograms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)

    private Integer Id;
    private String UUID;
    private String patientId;
    private String amrsPatientProgramID;
    private String patientKenyaemrUuid;
    private String kenyaemrProgramUuid;
    private String programName;
    private String programUUID;
    private String locationId;
    private String locationUUID;
    private String conceptId;
    private String dateEnrolled;
    private String dateCompleted;
    private String parentLocationUuid;
    private int migrated;
    private String responseCode;

    private int programID;

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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientKenyaemrUuid() {
        return patientKenyaemrUuid;
    }

    public void setPatientKenyaemrUuid(String patientKenyaemrUuid) {
        this.patientKenyaemrUuid = patientKenyaemrUuid;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationUUID() {
        return locationUUID;
    }

    public void setLocationUUID(String locationUUID) {
        this.locationUUID = locationUUID;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public String getDateEnrolled() {
        return dateEnrolled;
    }

    public void setDateEnrolled(String dateEnrolled) {
        this.dateEnrolled = dateEnrolled;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getParentLocationUuid() {
        return parentLocationUuid;
    }

    public void setParentLocationUuid(String parentLocationUuid) {
        this.parentLocationUuid = parentLocationUuid;
    }

    public int getMigrated() {
        return migrated;
    }

    public void setMigrated(int migrated) {
        this.migrated = migrated;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public int getProgramID() {
        return programID;
    }

    public void setProgramID(int programID) {
        this.programID = programID;
    }

    public String getProgramUUID() {
        return programUUID;
    }

    public void setProgramUUID(String programUUID) {
        this.programUUID = programUUID;
    }

    public String getKenyaemrProgramUuid() {
        return kenyaemrProgramUuid;
    }

    public void setKenyaemrProgramUuid(String kenyaemrProgramUuid) {
        this.kenyaemrProgramUuid = kenyaemrProgramUuid;
    }

    public String getAmrsPatientProgramID() {
        return amrsPatientProgramID;
    }

    public void setAmrsPatientProgramID(String amrsPatientProgramID) {
        this.amrsPatientProgramID = amrsPatientProgramID;
    }
}
