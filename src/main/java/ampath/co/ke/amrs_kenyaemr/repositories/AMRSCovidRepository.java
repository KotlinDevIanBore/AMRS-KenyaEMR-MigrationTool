package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSCovid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSCovidRepository extends JpaRepository<AMRSCovid, Long> {
    List<AMRSCovid> findByResponseCodeIsNull();

    List<AMRSCovid> findByEncounterId(String encounterId);

    List<AMRSCovid> findByVisitId(String visitId);

    List<AMRSCovid> findByEncounterIdAndConceptIdAndPatientId(String encounterId, String conceptId, String patientId);
}
