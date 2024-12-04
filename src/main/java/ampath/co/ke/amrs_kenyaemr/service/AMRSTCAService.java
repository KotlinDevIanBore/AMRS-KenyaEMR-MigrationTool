package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSTcas;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSTCARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("amrstcaservice")
public class AMRSTCAService {

    private AMRSTCARepository amrstcaRepository;


    @Autowired
    public AMRSTCAService(AMRSTCARepository amrstcaRepository){
        this.amrstcaRepository=amrstcaRepository;
    }

    public List<AMRSTcas> findall(){
        return amrstcaRepository.findAll();
    }

    public List<AMRSTcas> findByUuid(String uuid){
        return amrstcaRepository.findByUuid(uuid);
    }

    public List<AMRSTcas> findByResponseCodeIsNull(){
        return amrstcaRepository.findByResponseCodeIsNull();
    }

    public AMRSTcas save (AMRSTcas amrsTcas){
        return amrstcaRepository.save(amrsTcas);
    }
}
