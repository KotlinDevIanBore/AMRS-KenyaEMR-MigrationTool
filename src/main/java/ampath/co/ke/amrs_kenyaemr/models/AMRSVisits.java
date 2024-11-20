package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "visits")
@Entity
public class AMRSVisits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)

    private Integer id;
    private String visitId;
    private String patientId;
    private  String visitType;
    private String dateStarted;
    private String dateStop;
    private String locationId;
    private  String responseCode;
    private String encounterType;
    private String kenyaemrPatientUuid;
    private String kenyaemrVisitUuid;
    private String voided;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getDateStop() {
        return dateStop;
    }

    public void setDateStop(String dateStop) {
        this.dateStop = dateStop;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getVoided() {
        return voided;
    }

    public void setVoided(String voided) {
        this.voided = voided;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(String encounterType) {
        this.encounterType = encounterType;
    }

    public String getKenyaemrPatientUuid() {
        return kenyaemrPatientUuid;
    }

    public void setKenyaemrPatientUuid(String kenyaemrPatientUuid) {
        this.kenyaemrPatientUuid = kenyaemrPatientUuid;
    }

    public String getKenyaemrVisitUuid() {
        return kenyaemrVisitUuid;
    }

    public void setKenyaemrVisitUuid(String kenyaemrVisitUuid) {
        this.kenyaemrVisitUuid = kenyaemrVisitUuid;
    }
}
