package ampath.co.ke.amrs_kenyaemr.repositories;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPrepInitial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSPrepInitialRepositiory extends JpaRepository<AMRSPrepInitial, Long> {

    List<AMRSPrepInitial> findAll();
    List<AMRSPrepInitial> findByPatientIdAndEncounterIdAndConceptId(String pid, String encid, String cid);
    List<AMRSPrepInitial> findFirstByOrderByIdDesc();
    List<AMRSPrepInitial> findByResponseCodeIsNull();
    List<AMRSPrepInitial> findByPatientId(String pid);
    List<AMRSPrepInitial> findByVisitId(String vid);
}
