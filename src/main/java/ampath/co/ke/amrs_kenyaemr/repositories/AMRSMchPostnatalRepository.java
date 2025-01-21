package ampath.co.ke.amrs_kenyaemr.repositories;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMchPostnatal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSMchPostnatalRepository extends JpaRepository<AMRSMchPostnatal, Long> {


    List<AMRSMchPostnatal> findAll();
    List<AMRSMchPostnatal> findByPatientIdAndEncounterIdAndConceptId(String pid, String encid, String cid);
    List<AMRSMchPostnatal> findFirstByOrderByIdDesc();
    List<AMRSMchPostnatal> findByResponseCodeIsNull();
    List<AMRSMchPostnatal> findByPatientId(String pid);
    List<AMRSMchPostnatal> findByVisitId(String vid);
}
