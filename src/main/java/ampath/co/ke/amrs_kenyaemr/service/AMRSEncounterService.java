package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSConceptMapper;
import ampath.co.ke.amrs_kenyaemr.models.AMRSEncounters;
import ampath.co.ke.amrs_kenyaemr.models.AMRSIdentifiers;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSEncountersRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSEnrollmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("encountersService")
public class AMRSEncounterService {
    Date nowDate = new Date();
    private AMRSEncountersRepository amrsEncountersRepository;
    @Autowired
    public AMRSEncounterService(AMRSEncountersRepository amrsEncountersRepository) {
        this.amrsEncountersRepository = amrsEncountersRepository;
    }

    public List<AMRSEncounters> getAll() {
        return amrsEncountersRepository.findAll();
    }
    public List<AMRSEncounters> findByPatientIdAndEncounterIDAndConceptId(String pid, String encountid, String concept) {
        return amrsEncountersRepository.findByPatientIdAndEncounterIDAndConceptId( pid, encountid, concept);
    }



    public AMRSEncounters save(AMRSEncounters dataset) {
        return amrsEncountersRepository.save(dataset);
    }


}
