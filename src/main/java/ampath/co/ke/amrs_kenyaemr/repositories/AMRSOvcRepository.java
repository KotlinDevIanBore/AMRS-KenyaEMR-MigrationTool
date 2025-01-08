package ampath.co.ke.amrs_kenyaemr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ampath.co.ke.amrs_kenyaemr.models.AMRSOvc;

import java.util.List;

public interface AMRSOvcRepository extends JpaRepository<AMRSOvc, Long> {

    List<AMRSOvc> findAll();
    List<AMRSOvc> findByPatientIdAndEncounterIdAndConceptId(String pid,String encid,String cid);
    List<AMRSOvc> findFirstByOrderByIdDesc();
    List<AMRSOvc> findByResponseCodeIsNull();
    List<AMRSOvc> findByPatientId(String pid);
    List<AMRSOvc> findByVisitId(String vid);
}
