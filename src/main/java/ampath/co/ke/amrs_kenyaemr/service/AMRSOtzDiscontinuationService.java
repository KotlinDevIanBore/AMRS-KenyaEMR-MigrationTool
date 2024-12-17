package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSOtzDiscontinuation;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSOtzDiscontinuationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("amrsotzadiscontinuationservice")
public class AMRSOtzDiscontinuationService {
    private AMRSOtzDiscontinuationRepository amrsOtzDiscontinuationRepository;
    @Autowired
    public AMRSOtzDiscontinuationService(AMRSOtzDiscontinuationRepository amrsOtzDiscontinuationRepository){
        this.amrsOtzDiscontinuationRepository = amrsOtzDiscontinuationRepository;
    }

    public List<AMRSOtzDiscontinuation> findall(){
        return amrsOtzDiscontinuationRepository.findAll();
    }


    public List<AMRSOtzDiscontinuation> findByResponseCodeIsNull(){
        return amrsOtzDiscontinuationRepository.findByResponseCodeIsNull();
    }

    public List<AMRSOtzDiscontinuation> findByEncounterId(String encounterId){
        return amrsOtzDiscontinuationRepository.findByEncounterId(encounterId);
    }

    public List<AMRSOtzDiscontinuation> findByVisitId(String visitId){
        return amrsOtzDiscontinuationRepository.findByVisitId(visitId);
    }

    public AMRSOtzDiscontinuation save (AMRSOtzDiscontinuation amrsGreenCard){
        return amrsOtzDiscontinuationRepository.save(amrsGreenCard);
    }
}
