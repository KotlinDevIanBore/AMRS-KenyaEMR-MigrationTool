package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSAlcohol;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSAlcoholRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("amrsalcoholservice")
public class AMRSAlcoholService {
  private final AMRSAlcoholRepository amrsAlcoholRepository;
  @Autowired
  public AMRSAlcoholService(AMRSAlcoholRepository AMRSAlcoholRepository){
    this.amrsAlcoholRepository = AMRSAlcoholRepository;
  }

  public List<AMRSAlcohol> findall(){
    return amrsAlcoholRepository.findAll();
  }


  public List<AMRSAlcohol> findByResponseCodeIsNull(){
    return amrsAlcoholRepository.findByResponseCodeIsNull();
  }

  public List<AMRSAlcohol> findByEncounterId(String encounterId){
    return amrsAlcoholRepository.findByEncounterId(encounterId);
  }

  public List<AMRSAlcohol> findByVisitId(String visitId){
    return amrsAlcoholRepository.findByVisitId(visitId);
  }

  public AMRSAlcohol save (AMRSAlcohol AMRSAlcohol){
    return amrsAlcoholRepository.save(AMRSAlcohol);
  }

  public List<AMRSAlcohol> findByEncounterConceptAndPatient(String encounterId, String conceptId, String patientId) {
    return amrsAlcoholRepository.findByEncounterIdAndConceptIdAndPatientId(encounterId, conceptId, patientId);
  }
}
