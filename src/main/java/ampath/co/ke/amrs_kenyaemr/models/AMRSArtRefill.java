package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;


@Table(name = "art_refill")
@Entity
public class AMRSArtRefill {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer Id;
    private String patientId;
    private String encounterId;
    private String formId;
    private String conceptId;
    private String kenyaEmrEncounterUuid;
    private String kenyaEmrConceptUuid;
    private String kenyaEmrPatientUuid;
    private String responseCode;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }


    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(String encounterID) {
        this.encounterId = encounterID;
    }


    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }


    public String getKenyaEmrEncounterUuid() {
        return kenyaEmrEncounterUuid;
    }

    public void setKenyaEmrEncounterUuid(String kenyaEmrEncounterUuid) {
        this.kenyaEmrEncounterUuid = kenyaEmrEncounterUuid;
    }

    public String getKenyaEmrConceptUuid() {
        return kenyaEmrConceptUuid;
    }

    public void setKenyaEmrConceptUuid(String kenyaEmrConceptUuid) {
        this.kenyaEmrConceptUuid = kenyaEmrConceptUuid;
    }

    public String getKenyaEmrPatientUuid() {
        return kenyaEmrPatientUuid;
    }

    public void setKenyaEmrPatientUuid(String kenyaEmrPatientUuid) {
        this.kenyaEmrPatientUuid = kenyaEmrPatientUuid;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
