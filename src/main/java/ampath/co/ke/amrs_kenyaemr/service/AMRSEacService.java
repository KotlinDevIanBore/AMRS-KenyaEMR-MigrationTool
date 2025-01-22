package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEac;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSEacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("amrseacservice")
public class AMRSEacService {
  private final AMRSEacRepository amrsEacRepository;
  @Autowired
  public AMRSEacService(AMRSEacRepository AMRSEacRepository){
    this.amrsEacRepository = AMRSEacRepository;
  }

  public List<AMRSEac> findall(){
    return amrsEacRepository.findAll();
  }

  public List<AMRSEac> findByResponseCodeIsNull(){
    return amrsEacRepository.findByResponseCodeIsNull();
  }

  public List<AMRSEac> findByEncounterId(String encounterId){
    return amrsEacRepository.findByEncounterId(encounterId);
  }

  public List<AMRSEac> findByVisitId(String visitId){
    return amrsEacRepository.findByVisitId(visitId);
  }

  public AMRSEac save (AMRSEac AMRSAlcohol){
    return amrsEacRepository.save(AMRSAlcohol);
  }

  public List<AMRSEac> findByEncounterConceptAndPatient(String encounterId, String conceptId, String patientId) {
    return amrsEacRepository.findByEncounterIdAndConceptIdAndPatientId(encounterId, conceptId, patientId);
  }
}
