package ampath.co.ke.amrs_kenyaemr.repositories;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPrepFollowUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSPrepFollowUpRepository extends JpaRepository<AMRSPrepFollowUp, Long> {

    List<AMRSPrepFollowUp> findAll();
    List<AMRSPrepFollowUp> findByPatientIdAndEncounterIdAndConceptId(String pid, String encid, String cid);
    List<AMRSPrepFollowUp> findFirstByOrderByIdDesc();
    List<AMRSPrepFollowUp> findByResponseCodeIsNull();
    List<AMRSPrepFollowUp> findByPatientId(String pid);
    List<AMRSPrepFollowUp> findByVisitId(String vid);
}
