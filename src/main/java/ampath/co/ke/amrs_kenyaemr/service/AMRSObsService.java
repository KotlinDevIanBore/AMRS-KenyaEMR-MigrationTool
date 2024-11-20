package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSObs;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSObsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("encountersService")
public class AMRSObsService {
    Date nowDate = new Date();
    private AMRSObsRepository amrsEncountersRepository;
    @Autowired
    public AMRSObsService(AMRSObsRepository amrsEncountersRepository) {
        this.amrsEncountersRepository = amrsEncountersRepository;
    }

    public List<AMRSObs> getAll() {
        return amrsEncountersRepository.findAll();
    }
    public List<AMRSObs> findByPatientIdAndEncounterIDAndConceptId(String pid, String encountid, String concept) {
        return amrsEncountersRepository.findByPatientIdAndEncounterIDAndConceptId( pid, encountid, concept);
    }



    public AMRSObs save(AMRSObs dataset) {
        return amrsEncountersRepository.save(dataset);
    }


}
