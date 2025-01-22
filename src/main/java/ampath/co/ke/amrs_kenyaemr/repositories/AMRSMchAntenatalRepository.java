package ampath.co.ke.amrs_kenyaemr.repositories;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMchAntenatal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSMchAntenatalRepository extends JpaRepository<AMRSMchAntenatal, Long> {

    List<AMRSMchAntenatal> findAll();
    List<AMRSMchAntenatal> findByPatientIdAndEncounterIdAndConceptId(String pid, String encid, String cid);
    List<AMRSMchAntenatal> findFirstByOrderByIdDesc();
    List<AMRSMchAntenatal> findByResponseCodeIsNull();
    List<AMRSMchAntenatal> findByPatientId(String pid);
    List<AMRSMchAntenatal> findByVisitId(String vid);
}
