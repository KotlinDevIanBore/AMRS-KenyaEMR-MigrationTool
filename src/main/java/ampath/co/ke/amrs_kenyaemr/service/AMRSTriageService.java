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
  public List<AMRSTriage> findByPatientIdAndEncounterId(String pid,String eid) {
    return amrsTriageRepository.findByPatientIdAndEncounterId(pid,eid);
  }

  public List<AMRSTriage> findFirstByOrderByIdDesc() {
    return amrsTriageRepository.findFirstByOrderByIdDesc();
  }
  public List<AMRSTriage> findByResponseCodeIsNull() {
    return amrsTriageRepository.findByResponseCodeIsNull();
  }
  public AMRSTriage save(AMRSTriage dataset) {
    return amrsTriageRepository.save(dataset);
  }

}
