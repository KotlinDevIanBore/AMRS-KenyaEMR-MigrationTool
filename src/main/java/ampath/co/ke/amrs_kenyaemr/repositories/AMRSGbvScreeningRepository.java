package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSGBVScreening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSGbvScreeningRepository extends JpaRepository<AMRSGBVScreening, Long> {
    List<AMRSGBVScreening> findByResponseCodeIsNull();

    List<AMRSGBVScreening> findByEncounterId(String encounterId);

    List<AMRSGBVScreening> findByVisitId(String visitId);

    List<AMRSGBVScreening> findByEncounterIdAndConceptIdAndPatientId(String encounterId, String conceptId, String patientId);
}
