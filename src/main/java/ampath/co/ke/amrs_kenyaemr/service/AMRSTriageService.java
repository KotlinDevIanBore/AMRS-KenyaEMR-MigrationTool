package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPrograms;
import ampath.co.ke.amrs_kenyaemr.models.AMRSTriage;
import ampath.co.ke.amrs_kenyaemr.models.AMRSVisits;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSProgramsRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSTriageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("AMRSTriageService")
public class AMRSTriageService {
  Date nowDate = new Date();
  private AMRSTriageRepository amrsTriageRepository;

  @Autowired
  public AMRSTriageService(AMRSTriageRepository amrsTriageRepository) {
    this.amrsTriageRepository = amrsTriageRepository;
  }
  public List<AMRSTriage> findByPatientIdAndEncounterIdAndConceptId(String pid,String eid, String cid) {
    return amrsTriageRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
  }

  public List<AMRSTriage> findFirstByOrderByIdDesc() {
    return amrsTriageRepository.findFirstByOrderByIdDesc();
  }

  public AMRSTriage save(AMRSTriage dataset) {
    return amrsTriageRepository.save(dataset);
  }

}
