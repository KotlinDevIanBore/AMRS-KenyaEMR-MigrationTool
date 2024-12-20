package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSTbScreening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSTbScreeningRepository extends JpaRepository<AMRSTbScreening, Long> {
    List<AMRSTbScreening> findByResponseCodeIsNull();

    List<AMRSTbScreening> findByEncounterId(String encounterId);
    List<AMRSTbScreening> findByVisitId(String visitId);
    List<AMRSTbScreening> findByEncounterIdAndConceptIdAndPatientId(String encounterId, String conceptId, String patientId);
}
