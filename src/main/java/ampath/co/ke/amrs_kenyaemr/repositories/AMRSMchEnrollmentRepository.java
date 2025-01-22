package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSMchEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSMchEnrollmentRepository extends JpaRepository<AMRSMchEnrollment, Long> {


    List<AMRSMchEnrollment> findAll();
    List<AMRSMchEnrollment> findByPatientIdAndEncounterIdAndConceptId(String pid, String encid, String cid);
    List<AMRSMchEnrollment> findFirstByOrderByIdDesc();
    List<AMRSMchEnrollment> findByResponseCodeIsNull();
    List<AMRSMchEnrollment> findByPatientId(String pid);
    List<AMRSMchEnrollment> findByVisitId(String vid);
}
