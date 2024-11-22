package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "enrollments")
@Entity
public class AMRSEnrollments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer Id;
    private String patientId;
    private String encounterId;
    private String encounterDatetime;
    private String formId;
    private String formName;
    private String patientType;
    private String entryPoint;
    private String tiFacility;
    private String dateFirstEnrolledInCare;
    private String transferInDate;
    private String dateStartedArtAtTransferringFacility;
    private String dateConfirmedHivPositive;
    private String facilityConfirmedHivPositive;
    private String baselineArvUse;
    private String purposeOfBaselineArvUse;
    private String baselineArvRegimen;
    private String baselineArvRegimenLine;
    private String baselineArvDateLastUsed;
    private String baselineWhoStage;
    private String baselineCd4Results;
    private String baselineCd4Date;
    private String baselineVlResults;
    private String baselineVlDate;
    private String baselineVlLdlResults;
    private String baselineVlLdlDate;
    private String baselineHbvInfected;
    private String baselineTbInfected;
    private String baselinePregnant;
    private String baselineBreastFeeding;
    private String baselineWeight;
    private String baselineHeight;
    private String baselineBMI;
    private String nameOfTreatmentSupporter;
    private String relationshipOfTreatmentSupporter;
    private String treatmentSupporterTelephone;
    private String treatmentSupporterAddress;

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

    public String getEncounterDatetime() {
        return encounterDatetime;
    }

    public void setEncounterDatetime(String encounterDatetime) {
        this.encounterDatetime = encounterDatetime;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
    }

    public String getTiFacility() {
        return tiFacility;
    }

    public void setTiFacility(String tiFacility) {
        this.tiFacility = tiFacility;
    }

    public String getDateFirstEnrolledInCare() {
        return dateFirstEnrolledInCare;
    }

    public void setDateFirstEnrolledInCare(String dateFirstEnrolledInCare) {
        this.dateFirstEnrolledInCare = dateFirstEnrolledInCare;
    }

    public String getTransferInDate() {
        return transferInDate;
    }

    public void setTransferInDate(String transferInDate) {
        this.transferInDate = transferInDate;
    }

    public String getDateStartedArtAtTransferringFacility() {
        return dateStartedArtAtTransferringFacility;
    }

    public void setDateStartedArtAtTransferringFacility(String dateStartedArtAtTransferringFacility) {
        this.dateStartedArtAtTransferringFacility = dateStartedArtAtTransferringFacility;
    }

    public String getDateConfirmedHivPositive() {
        return dateConfirmedHivPositive;
    }

    public void setDateConfirmedHivPositive(String dateConfirmedHivPositive) {
        this.dateConfirmedHivPositive = dateConfirmedHivPositive;
    }

    public String getFacilityConfirmedHivPositive() {
        return facilityConfirmedHivPositive;
    }

    public void setFacilityConfirmedHivPositive(String facilityConfirmedHivPositive) {
        this.facilityConfirmedHivPositive = facilityConfirmedHivPositive;
    }

    public String getBaselineArvUse() {
        return baselineArvUse;
    }

    public void setBaselineArvUse(String baselineArvUse) {
        this.baselineArvUse = baselineArvUse;
    }

    public String getPurposeOfBaselineArvUse() {
        return purposeOfBaselineArvUse;
    }

    public void setPurposeOfBaselineArvUse(String purposeOfBaselineArvUse) {
        this.purposeOfBaselineArvUse = purposeOfBaselineArvUse;
    }

    public String getBaselineArvRegimen() {
        return baselineArvRegimen;
    }

    public void setBaselineArvRegimen(String baselineArvRegimen) {
        this.baselineArvRegimen = baselineArvRegimen;
    }

    public String getBaselineArvRegimenLine() {
        return baselineArvRegimenLine;
    }

    public void setBaselineArvRegimenLine(String baselineArvRegimenLine) {
        this.baselineArvRegimenLine = baselineArvRegimenLine;
    }

    public String getBaselineArvDateLastUsed() {
        return baselineArvDateLastUsed;
    }

    public void setBaselineArvDateLastUsed(String baselineArvDateLastUsed) {
        this.baselineArvDateLastUsed = baselineArvDateLastUsed;
    }

    public String getBaselineWhoStage() {
        return baselineWhoStage;
    }

    public void setBaselineWhoStage(String baselineWhoStage) {
        this.baselineWhoStage = baselineWhoStage;
    }

    public String getBaselineCd4Results() {
        return baselineCd4Results;
    }

    public void setBaselineCd4Results(String baselineCd4Results) {
        this.baselineCd4Results = baselineCd4Results;
    }

    public String getBaselineVlResults() {
        return baselineVlResults;
    }

    public void setBaselineVlResults(String baselineVlResults) {
        this.baselineVlResults = baselineVlResults;
    }

    public String getBaselineCd4Date() {
        return baselineCd4Date;
    }

    public void setBaselineCd4Date(String baselineCd4Date) {
        this.baselineCd4Date = baselineCd4Date;
    }

    public String getBaselineVlDate() {
        return baselineVlDate;
    }

    public void setBaselineVlDate(String baselineVlDate) {
        this.baselineVlDate = baselineVlDate;
    }

    public String getBaselineVlLdlResults() {
        return baselineVlLdlResults;
    }

    public void setBaselineVlLdlResults(String baselineVlLdlResults) {
        this.baselineVlLdlResults = baselineVlLdlResults;
    }

    public String getBaselineVlLdlDate() {
        return baselineVlLdlDate;
    }

    public void setBaselineVlLdlDate(String baselineVlLdlDate) {
        this.baselineVlLdlDate = baselineVlLdlDate;
    }

    public String getBaselineHbvInfected() {
        return baselineHbvInfected;
    }

    public void setBaselineHbvInfected(String baselineHbvInfected) {
        this.baselineHbvInfected = baselineHbvInfected;
    }

    public String getBaselineTbInfected() {
        return baselineTbInfected;
    }

    public void setBaselineTbInfected(String baselineTbInfected) {
        this.baselineTbInfected = baselineTbInfected;
    }

    public String getBaselinePregnant() {
        return baselinePregnant;
    }

    public void setBaselinePregnant(String baselinePregnant) {
        this.baselinePregnant = baselinePregnant;
    }

    public String getBaselineBreastFeeding() {
        return baselineBreastFeeding;
    }

    public void setBaselineBreastFeeding(String baselineBreastFeeding) {
        this.baselineBreastFeeding = baselineBreastFeeding;
    }

    public String getBaselineWeight() {
        return baselineWeight;
    }

    public void setBaselineWeight(String baselineWeight) {
        this.baselineWeight = baselineWeight;
    }

    public String getBaselineHeight() {
        return baselineHeight;
    }

    public void setBaselineHeight(String baselineHeight) {
        this.baselineHeight = baselineHeight;
    }

    public String getBaselineBMI() {
        return baselineBMI;
    }

    public void setBaselineBMI(String baselineBMI) {
        this.baselineBMI = baselineBMI;
    }

    public String getNameOfTreatmentSupporter() {
        return nameOfTreatmentSupporter;
    }

    public void setNameOfTreatmentSupporter(String nameOfTreatmentSupporter) {
        this.nameOfTreatmentSupporter = nameOfTreatmentSupporter;
    }

    public String getRelationshipOfTreatmentSupporter() {
        return relationshipOfTreatmentSupporter;
    }

    public void setRelationshipOfTreatmentSupporter(String relationshipOfTreatmentSupporter) {
        this.relationshipOfTreatmentSupporter = relationshipOfTreatmentSupporter;
    }

    public String getTreatmentSupporterTelephone() {
        return treatmentSupporterTelephone;
    }

    public void setTreatmentSupporterTelephone(String treatmentSupporterTelephone) {
        this.treatmentSupporterTelephone = treatmentSupporterTelephone;
    }

    public String getTreatmentSupporterAddress() {
        return treatmentSupporterAddress;
    }

    public void setTreatmentSupporterAddress(String treatmentSupporterAddress) {
        this.treatmentSupporterAddress = treatmentSupporterAddress;
    }
}
