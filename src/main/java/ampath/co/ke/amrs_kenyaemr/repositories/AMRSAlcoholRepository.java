package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSAlcohol;
import ampath.co.ke.amrs_kenyaemr.models.AMRSAlcohol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSAlcoholRepository extends JpaRepository<AMRSAlcohol, Long> {
  List<AMRSAlcohol> findByResponseCodeIsNull();

  List<AMRSAlcohol> findByEncounterId(String encounterId);

  List<AMRSAlcohol> findByVisitId(String visitId);

  List<AMRSAlcohol> findByEncounterIdAndConceptIdAndPatientId(String encounterId, String conceptId, String patientId);
}
