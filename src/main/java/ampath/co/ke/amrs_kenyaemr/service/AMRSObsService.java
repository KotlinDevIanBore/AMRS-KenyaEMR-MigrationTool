package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSObs;
import ampath.co.ke.amrs_kenyaemr.models.AMRSOrders;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSObsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("encountersService")
public class AMRSObsService {
    Date nowDate = new Date();
    private AMRSObsRepository amrsObsRepository;
    @Autowired
    public AMRSObsService(AMRSObsRepository amrsEncountersRepository) {
        this.amrsObsRepository = amrsEncountersRepository;
    }

    public List<AMRSObs> getAll() {
        return amrsObsRepository.findAll();
    }
    public List<AMRSObs> findByPatientIdAndEncounterIDAndConceptId(String pid, String encountid, String concept) {
        return amrsObsRepository.findByPatientIdAndEncounterIdAndConceptId( pid, encountid, concept);
    }
    public List<AMRSObs> findByEncounterId( String encountid) {
        return amrsObsRepository.findByEncounterId(encountid);
    }





    public AMRSObs save(AMRSObs dataset) {
        return amrsObsRepository.save(dataset);
    }

    public List<AMRSObs> findByResponseCodeIsNull(){
        return amrsObsRepository.findByResponseCodeIsNull();
    }


}
