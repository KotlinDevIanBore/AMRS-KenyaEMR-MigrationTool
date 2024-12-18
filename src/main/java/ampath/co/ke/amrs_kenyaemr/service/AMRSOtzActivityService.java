package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSOtzActivity;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSOtzActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("amrsotzactivityservice")
public class AMRSOtzActivityService {

    private final AMRSOtzActivityRepository amrsOtzActivityRepository;
    @Autowired
    public AMRSOtzActivityService(AMRSOtzActivityRepository amrsOtzActivityRepository){
        this.amrsOtzActivityRepository = amrsOtzActivityRepository;
    }

    public List<AMRSOtzActivity> findall(){
        return amrsOtzActivityRepository.findAll();
    }


    public List<AMRSOtzActivity> findByResponseCodeIsNull(){
        return amrsOtzActivityRepository.findByResponseCodeIsNull();
    }

    public List<AMRSOtzActivity> findByEncounterId(String encounterId){
        return amrsOtzActivityRepository.findByEncounterId(encounterId);
    }

    public List<AMRSOtzActivity> findByVisitId(String visitId){
        return amrsOtzActivityRepository.findByVisitId(visitId);
    }

    public AMRSOtzActivity save (AMRSOtzActivity amrsOtzActivity){
        return amrsOtzActivityRepository.save(amrsOtzActivity);
    }
}
