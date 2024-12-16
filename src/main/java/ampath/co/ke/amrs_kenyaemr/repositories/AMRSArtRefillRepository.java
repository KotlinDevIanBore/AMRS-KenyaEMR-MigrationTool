package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSArtRefill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSArtRefillRepository extends JpaRepository<AMRSArtRefill, Long> {

    List<AMRSArtRefill> findAll();
    List<AMRSArtRefill> findByPatientIdAndEncounterIdAndConceptId(String pid,String encid,String cid);
    List<AMRSArtRefill> findFirstByOrderByIdDesc();
    List<AMRSArtRefill> findByResponseCodeIsNull();
    List<AMRSArtRefill> findByPatientId(String pid);
}
