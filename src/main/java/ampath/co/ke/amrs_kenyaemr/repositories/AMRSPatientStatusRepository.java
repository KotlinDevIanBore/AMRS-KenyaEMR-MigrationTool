package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSPatientStatusRepository")
public interface AMRSPatientStatusRepository extends JpaRepository<AMRSPatientStatus, Long> {
    List<AMRSPatientStatus> findByPersonId(String pid);

    List<AMRSPatientStatus> findFirstByOrderByIdDesc();

    List<AMRSPatientStatus> findByResponseCodeIsNull();
    List<AMRSPatientStatus> findByPersonIdAndPersonAttributeTypeId(String pid,String attribute);
}
