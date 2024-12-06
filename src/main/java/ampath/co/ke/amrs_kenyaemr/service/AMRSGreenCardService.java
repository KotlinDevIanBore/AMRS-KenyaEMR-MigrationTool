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

    public List<AMRSGreenCard> findByUuid(String uuid){
        return amrsGreenCardRepository.findByUuid(uuid);
    }

    public List<AMRSGreenCard> findByResponseCodeIsNull(){
        return amrsGreenCardRepository.findByResponseCodeIsNull();
    }

    public AMRSGreenCard save (AMRSGreenCard amrsTcas){
        return amrsGreenCardRepository.save(amrsTcas);
    }
}
