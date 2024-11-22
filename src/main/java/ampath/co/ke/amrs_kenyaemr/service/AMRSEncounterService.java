package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncounters;
import ampath.co.ke.amrs_kenyaemr.models.AMRSIdentifiers;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSEncountersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("encounterService")
public class AMRSEncounterService {
    Date nowDate = new Date();

    private AMRSEncountersRepository amrsEncountersRepository;
    @Autowired
    public AMRSEncounterService(AMRSEncountersRepository amrsEncountersRepository){
        this.amrsEncountersRepository=amrsEncountersRepository;
    }
    public List<AMRSEncounters> findByPatientIdAndEncounterId(String pid, String encid) {
        return amrsEncountersRepository.findByPatientIdAndEncounterId(pid, encid);
    }
    public List<AMRSEncounters> findByEncounterId(String encid) {
        return amrsEncountersRepository.findByEncounterId(encid);
    }

    public List<AMRSEncounters> findFirstByOrderByIdDesc() {
        return amrsEncountersRepository.findFirstByOrderByIdDesc();
    }
    public List<AMRSEncounters> findByResponseCodeIsNull() {
        return amrsEncountersRepository.findByResponseCodeIsNullOrderByIdAsc();
    }

    public AMRSEncounters save(AMRSEncounters dataset) {
        return amrsEncountersRepository.save(dataset);
    }


}
