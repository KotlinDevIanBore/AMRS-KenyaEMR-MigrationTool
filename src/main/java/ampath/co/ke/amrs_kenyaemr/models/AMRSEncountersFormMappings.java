package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "amrsencountersformMappings")
@Entity
public class AMRSEncountersFormMappings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "patient_id")
    private String patientId;
    @Column(name = "amrs_encounter_id")
    private String amrsEncounterId;
    @Column(name = "amrs_form_id")
    private String amrsFormId;
    @Column(name = "kenyaemr_form_uuid")
    private String kenyaemrFormUuid;
    @Column(name = "amrs_migration_status", nullable = true)
    private boolean amrsMigrationStatus;
    @Column(name = "responseCode", nullable = true)
    private String responseCode;

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

    public String getAmrsEncounterId() {
        return amrsEncounterId;
    }

    public void setAmrsEncounterId(String amrsEncounterId) {
        this.amrsEncounterId = amrsEncounterId;
    }

    public String getAmrsFormId() {
        return amrsFormId;
    }

    public void setAmrsFormId(String amrsFormId) {
        this.amrsFormId = amrsFormId;
    }

    public String getKenyaemrFormUuid() {
        return kenyaemrFormUuid;
    }

    public void setKenyaemrFormUuid(String kenyaemrFormUuid) {
        this.kenyaemrFormUuid = kenyaemrFormUuid;
    }

    public boolean isAmrsMigrationStatus() {
        return amrsMigrationStatus;
    }

    public void setAmrsMigrationStatus(boolean amrsMigrationStatus) {
        this.amrsMigrationStatus = amrsMigrationStatus;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}