package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSDefaulterTracing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSDefaulterTracingRepository extends JpaRepository<AMRSDefaulterTracing, Long> {


    List<AMRSDefaulterTracing> findAll();
    List<AMRSDefaulterTracing> findByPatientIdAndEncounterIdAndConceptId(String pid,String encid,String cid);
    List<AMRSDefaulterTracing> findFirstByOrderByIdDesc();
    List<AMRSDefaulterTracing> findByResponseCodeIsNull();
    List<AMRSDefaulterTracing> findByPatientId(String pid);
    List<AMRSDefaulterTracing> findByVisitId(String vid);
}
