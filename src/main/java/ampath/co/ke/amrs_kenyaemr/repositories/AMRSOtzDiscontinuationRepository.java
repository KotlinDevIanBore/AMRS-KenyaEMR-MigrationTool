package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSOtzDiscontinuation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSOtzDiscontinuationRepository extends JpaRepository<AMRSOtzDiscontinuation, Long> {
    List<AMRSOtzDiscontinuation> findByResponseCodeIsNull();

    List<AMRSOtzDiscontinuation> findByEncounterId(String encounterId);

    List<AMRSOtzDiscontinuation> findByVisitId(String visitId);

    List<AMRSOtzDiscontinuation> findByEncounterIdAndConceptIdAndPatientId(String encounterId, String conceptId, String patientId);

}
