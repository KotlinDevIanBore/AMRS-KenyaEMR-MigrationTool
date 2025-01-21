package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSHeiOutcome;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSHeiOutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("amrsheioutcomeservice")
public class AMRSHeiOutcomeService {
    private final AMRSHeiOutcomeRepository amrsHeiOutcomeRepository;

    @Autowired
    public AMRSHeiOutcomeService(AMRSHeiOutcomeRepository amrsHeiOutcomeRepository) {
        this.amrsHeiOutcomeRepository = amrsHeiOutcomeRepository;
    }

    public List<AMRSHeiOutcome> findall() {
        return amrsHeiOutcomeRepository.findAll();
    }

    public List<AMRSHeiOutcome> findByResponseCodeIsNull() {
        return amrsHeiOutcomeRepository.findByResponseCodeIsNull();
    }

    public List<AMRSHeiOutcome> findByEncounterId(String encounterId) {
        return amrsHeiOutcomeRepository.findByEncounterId(encounterId);
    }

    public List<AMRSHeiOutcome> findByVisitId(String visitId) {
        return amrsHeiOutcomeRepository.findByVisitId(visitId);
    }

    public AMRSHeiOutcome save(AMRSHeiOutcome amrsHeiOutcome) {
        return amrsHeiOutcomeRepository.save(amrsHeiOutcome);
    }

    public List<AMRSHeiOutcome> findByEncounterConceptAndPatient(String encounterId, String conceptId, String patientId) {
        return amrsHeiOutcomeRepository.findByEncounterIdAndConceptIdAndPatientId(encounterId, conceptId, patientId);
    }
}

