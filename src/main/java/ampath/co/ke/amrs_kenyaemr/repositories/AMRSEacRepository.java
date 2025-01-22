package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEac;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSEacRepository extends JpaRepository<AMRSEac, Long> {
  List<AMRSEac> findByResponseCodeIsNull();

  List<AMRSEac> findByEncounterId(String encounterId);

  List<AMRSEac> findByVisitId(String visitId);

  List<AMRSEac> findByEncounterIdAndConceptIdAndPatientId(String encounterId, String conceptId, String patientId);
}
