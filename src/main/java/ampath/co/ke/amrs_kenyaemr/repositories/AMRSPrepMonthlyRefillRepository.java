package ampath.co.ke.amrs_kenyaemr.repositories;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPrepMonthlyRefill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSPrepMonthlyRefillRepository extends JpaRepository<AMRSPrepMonthlyRefill, Long> {

    List<AMRSPrepMonthlyRefill> findAll();
    List<AMRSPrepMonthlyRefill> findByPatientIdAndEncounterIdAndConceptId(String pid, String encid, String cid);
    List<AMRSPrepMonthlyRefill> findFirstByOrderByIdDesc();
    List<AMRSPrepMonthlyRefill> findByResponseCodeIsNull();
    List<AMRSPrepMonthlyRefill> findByPatientId(String pid);
    List<AMRSPrepMonthlyRefill> findByVisitId(String vid);
}
