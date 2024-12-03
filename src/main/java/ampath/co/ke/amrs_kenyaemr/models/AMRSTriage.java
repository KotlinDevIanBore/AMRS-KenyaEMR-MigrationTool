package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "amrs_triage")
@Entity
public class AMRSTriage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)

    private Integer Id;
    private String patientId;
    private String encounterId;
    private String encounterDateTime;
    private String visitId;
    private String locationId;
    private String obsDateTime;
    private String conceptId;
  private String kenyaemConceptId;
    private String value;

    /* private String heightAgeZscore;
    private String weightHeightZscore;
    private String bmiAgeZscore;
    private String bmi;
    private String muacMm;
    private String systolicBp;
    private String diastolicBp;
    private String pulse;
    private String temperature;
    private String spo2;
    private String rr;
    private String weight;
    private String height; */
  private String responseCode;
  private String kenyaemrFormUuid;
  private String kenyaemrEncounterUuid;
  private String kenyaemrVisitUuid;

  private String kenyaemrPatientUuid;


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

  public void setEncounterId(String encounterId) {
    this.encounterId = encounterId;
  }

  public String getEncounterDateTime() {
    return encounterDateTime;
  }

  public void setEncounterDateTime(String encounterDateTime) {
    this.encounterDateTime = encounterDateTime;
  }

  public String getVisitId() {
    return visitId;
  }

  public void setVisitId(String visitId) {
    this.visitId = visitId;
  }

  public String getLocationId() {
    return locationId;
  }

  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

  public String getObsDateTime() {
    return obsDateTime;
  }

  public void setObsDateTime(String obsDateTime) {
    this.obsDateTime = obsDateTime;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public String getKenyaemrFormUuid() {
    return kenyaemrFormUuid;
  }

  public void setKenyaemrFormUuid(String kenyaemrFormUuid) {
    this.kenyaemrFormUuid = kenyaemrFormUuid;
  }

  public String getKenyaemrEncounterUuid() {
    return kenyaemrEncounterUuid;
  }

  public void setKenyaemrEncounterUuid(String kenyaemrEncounterUuid) {
    this.kenyaemrEncounterUuid = kenyaemrEncounterUuid;
  }

  public String getKenyaemrVisitUuid() {
    return kenyaemrVisitUuid;
  }

  public void setKenyaemrVisitUuid(String kenyaemrVisitUuid) {
    this.kenyaemrVisitUuid = kenyaemrVisitUuid;
  }

  public String getConceptId() {
    return conceptId;
  }

  public void setConceptId(String conceptId) {
    this.conceptId = conceptId;
  }

  public String getKenyaemrPatientUuid() {
    return kenyaemrPatientUuid;
  }

  public void setKenyaemrPatientUuid(String kenyaemrPatientUuid) {
    this.kenyaemrPatientUuid = kenyaemrPatientUuid;
  }

  public String getKenyaemConceptId() {
    return kenyaemConceptId;
  }

  public void setKenyaemConceptId(String kenyaemConceptId) {
    this.kenyaemConceptId = kenyaemConceptId;
  }
}
