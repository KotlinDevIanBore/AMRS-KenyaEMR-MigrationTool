package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "encounters")
@Entity
public class AMRSEncounters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer Id;
    private String patientId;
    //    private String amrsUUID;
    private String encounterId;
    private String kenyaemrEncounterUuid;
    private String encounterTypeId;
    private String encounterName;
    private String locationId;
    private String visitId;
    private String kenyaemrEncounterTypeId;
    private String kenyaemrEncounterTypeUuid;
    private int responseCode;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

//    public String getAmrsUUID() {
//        return amrsUUID;
//    }
//
//    public void setAmrsUUID(String amrsUUID) {
//        this.amrsUUID = amrsUUID;
//    }

    public String getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(String encounterId) {
        this.encounterId = encounterId;
    }

    public String getEncounterTypeId() {
        return encounterTypeId;
    }

    public void setEncounterTypeId(String encounterTypeId) {
        this.encounterTypeId = encounterTypeId;
    }

    public String getEncounterName() {
        return encounterName;
    }

    public void setEncounterName(String encounterName) {
        this.encounterName = encounterName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getKenyaemrEncountyUuid() {
        return kenyaemrEncounterUuid;
    }

    public void setKenyaemrEncountyUuid(String kenyaemrEncountyUuid) {
        this.kenyaemrEncounterUuid = kenyaemrEncountyUuid;
    }

    public String getKenyaemrEncounterTypeId() {
        return kenyaemrEncounterTypeId;
    }

    public void setKenyaemrEncounterTypeId(String kenyaemrEncounterTypeId) {
        this.kenyaemrEncounterTypeId = kenyaemrEncounterTypeId;
    }

    public String getKenyaemrEncounterTypeUuid() {
        return kenyaemrEncounterTypeUuid;
    }

    public void setKenyaemrEncounterTypeUuid(String kenyaemrEncounterTypeUuid) {
        this.kenyaemrEncounterTypeUuid = kenyaemrEncounterTypeUuid;
    }
}
