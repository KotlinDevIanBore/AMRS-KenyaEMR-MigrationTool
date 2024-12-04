package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "amrs_tcas")
@Entity
public class AMRSTcas {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;


    private String patientId;

    private String formId;

    private String ConceptId;

    private String EncounterId;

    private String tca;

    private  String responseCode;

    @Column(name = "amrs_uuid", length = 36)
    private String uuid;

    private String kenyaemrPatientUuid;

    private String kenyaEmrEncounterUuid;

    private String kenyaEmrConceptUuid;

    private String obsDateTime;

    private String kenyaEmrTcaUuid;

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

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getConceptId() {
        return ConceptId;
    }

    public void setConceptId(String conceptId) {
        ConceptId = conceptId;
    }

    public String getEncounterId() {
        return EncounterId;
    }

    public void setEncounterId(String encounterId) {
        EncounterId = encounterId;
    }

    public String getTca() {
        return tca;
    }

    public void setTca(String tca) {
        this.tca = tca;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getKenyaemrPatientUuid() {
        return kenyaemrPatientUuid;
    }

    public void setKenyaemrPatientUuid(String kenyaemrPatientUuid) {
        this.kenyaemrPatientUuid = kenyaemrPatientUuid;
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

    public String getObsDateTime() {
        return obsDateTime;
    }

    public void setObsDateTime(String obsDateTime) {
        this.obsDateTime = obsDateTime;
    }

    public String getKenyaEmrTcaUuid() {
        return kenyaEmrTcaUuid;
    }

    public void setKenyaEmrTcaUuid(String kenyaEmrTcaUuid) {
        this.kenyaEmrTcaUuid = kenyaEmrTcaUuid;
    }
}
