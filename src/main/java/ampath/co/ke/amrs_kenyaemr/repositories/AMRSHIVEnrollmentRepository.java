package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEnrollments;
import ampath.co.ke.amrs_kenyaemr.models.AMRSHIVEnrollment;
import ampath.co.ke.amrs_kenyaemr.models.AMRSTriage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSHIVEnrollmentRepository extends JpaRepository<AMRSHIVEnrollment, Long> {
    AMRSHIVEnrollment findById(int pid);
   // List<AMRSHIVEnrollment> findByPersonID(String pid);
   // List<AMRSHIVEnrollment> findByUUID(String uuid);
    List<AMRSHIVEnrollment> findAll();
    List<AMRSHIVEnrollment> findByPatientIdAndEncounterIDAndConceptId(String pid,String encid,String cid);
    List<AMRSHIVEnrollment> findFirstByOrderByIdDesc();
    List<AMRSHIVEnrollment> findByResponseCodeIsNull();
    List<AMRSHIVEnrollment> findByPatientId(String pid);
}
