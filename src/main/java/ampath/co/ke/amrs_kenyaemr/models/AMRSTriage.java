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
    private String heightAgeZscore;
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
    private String height;
  private String responseCode;
  private String kenyaemrFormUuid;
  private String kenyaemrEncounterUuid;


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

  public String getHeightAgeZscore() {
    return heightAgeZscore;
  }

  public void setHeightAgeZscore(String heightAgeZscore) {
    this.heightAgeZscore = heightAgeZscore;
  }

  public String getWeightHeightZscore() {
    return weightHeightZscore;
  }

  public void setWeightHeightZscore(String weightHeightZscore) {
    this.weightHeightZscore = weightHeightZscore;
  }

  public String getBmiAgeZscore() {
    return bmiAgeZscore;
  }

  public void setBmiAgeZscore(String bmiAgeZscore) {
    this.bmiAgeZscore = bmiAgeZscore;
  }

  public String getBmi() {
    return bmi;
  }

  public void setBmi(String bmi) {
    this.bmi = bmi;
  }

  public String getMuacMm() {
    return muacMm;
  }

  public void setMuacMm(String muacMm) {
    this.muacMm = muacMm;
  }

  public String getSystolicBp() {
    return systolicBp;
  }

  public void setSystolicBp(String systolicBp) {
    this.systolicBp = systolicBp;
  }

  public String getDiastolicBp() {
    return diastolicBp;
  }

  public void setDiastolicBp(String diastolicBp) {
    this.diastolicBp = diastolicBp;
  }

  public String getPulse() {
    return pulse;
  }

  public void setPulse(String pulse) {
    this.pulse = pulse;
  }

  public String getTemperature() {
    return temperature;
  }

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }

  public String getSpo2() {
    return spo2;
  }

  public void setSpo2(String spo2) {
    this.spo2 = spo2;
  }

  public String getRr() {
    return rr;
  }

  public void setRr(String rr) {
    this.rr = rr;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
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

}
