package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSGreenCard;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSGreenCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("amrstcaservice")
public class AMRSGreenCardService {

    private AMRSGreenCardRepository amrsGreenCardRepository;
    @Autowired
    public AMRSGreenCardService(AMRSGreenCardRepository amrsGreenCardRepository){
        this.amrsGreenCardRepository = amrsGreenCardRepository;
    }

    public List<AMRSGreenCard> findall(){
        return amrsGreenCardRepository.findAll();
    }


    public List<AMRSGreenCard> findByResponseCodeIsNull(){
        return amrsGreenCardRepository.findByResponseCodeIsNull();
    }

    public List<AMRSGreenCard> findByEncounterId(String encounterId){
        return amrsGreenCardRepository.findByEncounterId(encounterId);
    }

    public List<AMRSGreenCard> findByVisitId(String visitId){
        return amrsGreenCardRepository.findByVisitId(visitId);
    }
    public List<AMRSGreenCard> findByPatientIdAndVisitIdAndConceptId(String patientId,String visitId,String conceptId){
        return amrsGreenCardRepository.findByPatientIdAndVisitIdAndConceptId(patientId, visitId,conceptId);
    }


    public AMRSGreenCard save (AMRSGreenCard amrsGreenCard){
        return amrsGreenCardRepository.save(amrsGreenCard);
    }
}
