package ampath.co.ke.amrs_kenyaemr.repositories;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMchDischargeAndReferral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSMchDischargeAndReferralRepository extends JpaRepository<AMRSMchDischargeAndReferral, Long> {


    List<AMRSMchDischargeAndReferral> findAll();
    List<AMRSMchDischargeAndReferral> findByPatientIdAndEncounterIdAndConceptId(String pid, String encid, String cid);
    List<AMRSMchDischargeAndReferral> findFirstByOrderByIdDesc();
    List<AMRSMchDischargeAndReferral> findByResponseCodeIsNull();
    List<AMRSMchDischargeAndReferral> findByPatientId(String pid);
    List<AMRSMchDischargeAndReferral> findByVisitId(String vid);
}
