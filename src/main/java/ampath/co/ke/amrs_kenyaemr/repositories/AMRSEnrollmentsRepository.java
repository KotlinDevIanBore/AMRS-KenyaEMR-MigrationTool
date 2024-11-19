package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEnrollments;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSEnrollmentsRepository extends JpaRepository<AMRSEnrollments, Long> {
    AMRSEnrollments findById(int pid);
    List<AMRSEnrollments> findByPersonId(String pid);
    List<AMRSEnrollments> findByUUID(String uuid);
    List<AMRSEnrollments> findAll();
   // List<AMRSEnrollments> findByUUIDAndParentlocationUUID(String uuid,String location);
    List<AMRSEnrollments> findFirstByOrderByIdDesc();
}