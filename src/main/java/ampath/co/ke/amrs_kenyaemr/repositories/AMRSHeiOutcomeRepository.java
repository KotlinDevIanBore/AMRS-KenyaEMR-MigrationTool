package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSHeiOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSHeiOutcomeRepository extends JpaRepository<AMRSHeiOutcome, Long> {
    List<AMRSHeiOutcome> findByResponseCodeIsNull();

    List<AMRSHeiOutcome> findByEncounterId(String encounterId);

    List<AMRSHeiOutcome> findByVisitId(String visitId);

    List<AMRSHeiOutcome> findByEncounterIdAndConceptIdAndPatientId(String encounterId, String conceptId, String patientId);
}

