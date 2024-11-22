package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "triage")
@Entity
public class AMRSTriage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)

    private Integer Id;
    private String patientId;
    private String encounterID;
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

//    private String locationUuid;
//    private String patientId;
//    private String provider;
//    private String encounterID;
//    private String encounterDateTime;
//    private String encounterType;
//    private String conceptId;
//    private String conceptName;
//    private String obsDateTime;
//    private String conceptValue;
//    private String valueDataType;
//    private String dataTypeId;
//    private String encounterName;
//    private String Category;
//    private String kenyaemrEncounterUuid;
//    private String kenyaemrConceptUuid;
//    private String kenyaemrValue;
//    private int responseCode;

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

  public String getEncounterID() {
    return encounterID;
  }

  public void setEncounterID(String encounterID) {
    this.encounterID = encounterID;
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

  //  public String getLocationUuid() {
//    return locationUuid;
//  }
//
//  public void setLocationUuid(String locationUuid) {
//    this.locationUuid = locationUuid;
//  }
//
//  public String getPatientId() {
//    return patientId;
//  }
//
//  public void setPatientId(String patientId) {
//    this.patientId = patientId;
//  }
//
//  public String getEncounterID() {
//    return encounterID;
//  }
//
//  public void setEncounterID(String encounterID) {
//    this.encounterID = encounterID;
//  }
//
//  public String getEncounterDateTime() {
//    return encounterDateTime;
//  }
//
//  public void setEncounterDateTime(String encounterDateTime) {
//    this.encounterDateTime = encounterDateTime;
//  }
//
//  public String getEncounterType() {
//    return encounterType;
//  }
//
//  public void setEncounterType(String encounterType) {
//    this.encounterType = encounterType;
//  }
//
//  public String getConceptId() {
//    return conceptId;
//  }
//
//  public void setConceptId(String conceptId) {
//    this.conceptId = conceptId;
//  }
//
//  public String getConceptName() {
//    return conceptName;
//  }
//
//  public void setConceptName(String conceptName) {
//    this.conceptName = conceptName;
//  }
//
//  public String getObsDateTime() {
//    return obsDateTime;
//  }
//
//  public void setObsDateTime(String obsDateTime) {
//    this.obsDateTime = obsDateTime;
//  }
//
//  public String getConceptValue() {
//    return conceptValue;
//  }
//
//  public String getProvider() {
//    return provider;
//  }
//
//  public void setProvider(String provider) {
//    this.provider = provider;
//  }
//
//  public void setConceptValue(String conceptValue) {
//    this.conceptValue = conceptValue;
//  }
//
//  public String getValueDataType() {
//    return valueDataType;
//  }
//
//  public void setValueDataType(String valueDataType) {
//    this.valueDataType = valueDataType;
//  }
//
//  public String getDataTypeId() {
//    return dataTypeId;
//  }
//
//  public void setDataTypeId(String dataTypeId) {
//    this.dataTypeId = dataTypeId;
//  }
//
//  public String getEncounterName() {
//    return encounterName;
//  }
//
//  public void setEncounterName(String encounterName) {
//    this.encounterName = encounterName;
//  }
//
//  public String getCategory() {
//    return Category;
//  }
//
//  public void setCategory(String category) {
//    Category = category;
//  }
//
//  public String getKenyaemrEncounterUuid() {
//    return kenyaemrEncounterUuid;
//  }
//
//  public void setKenyaemrEncounterUuid(String kenyaemrEncounterUuid) {
//    this.kenyaemrEncounterUuid = kenyaemrEncounterUuid;
//  }
//
//  public String getKenyaemrConceptUuid() {
//    return kenyaemrConceptUuid;
//  }
//
//  public void setKenyaemrConceptUuid(String kenyaemrConceptUuid) {
//    this.kenyaemrConceptUuid = kenyaemrConceptUuid;
//  }
//
//  public String getKenyaemrValue() {
//    return kenyaemrValue;
//  }
//
//  public void setKenyaemrValue(String kenyaemrValue) {
//    this.kenyaemrValue = kenyaemrValue;
//  }
//
//  public int getResponseCode() {
//    return responseCode;
//  }
//
//  public void setResponseCode(int responseCode) {
//    this.responseCode = responseCode;
//  }
}
