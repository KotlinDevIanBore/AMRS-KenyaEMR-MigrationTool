package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSOtzEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSOtzEnrollmentRepository extends JpaRepository<AMRSOtzEnrollment, Long> {
    List<AMRSOtzEnrollment> findByResponseCodeIsNull();

    List<AMRSOtzEnrollment> findByEncounterId(String encounterId);

    List<AMRSOtzEnrollment> findByVisitId(String visitId);

    List<AMRSOtzEnrollment> findByEncounterIdAndConceptIdAndPatientId(String encounterId, String conceptId, String patientId);
}
