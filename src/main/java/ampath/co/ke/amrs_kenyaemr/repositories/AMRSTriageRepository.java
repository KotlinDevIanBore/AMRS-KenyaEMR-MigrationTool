package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPrograms;
import ampath.co.ke.amrs_kenyaemr.models.AMRSTriage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSTriageRepository extends JpaRepository<AMRSTriage, Long> {
  AMRSTriage findById(int pid);
  //List<AMRSTriage> findByUuid(String cuuid);
  List<AMRSTriage> findByPatientIdAndEncounterId(String pid,String encid);
  List<AMRSTriage> findByPatientIdAndEncounterIdAndConceptId(String pid,String encid,String conceptid);
  List<AMRSTriage> findFirstByOrderByIdDesc();
  List<AMRSTriage> findByResponseCodeIsNull();
  List<AMRSTriage> findByEncounterId(String encid);
  List<AMRSTriage> findByVisitId(String encid);
}
