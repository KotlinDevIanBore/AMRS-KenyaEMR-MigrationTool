package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncountersFormMappings;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSEncountersFormMappingRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSFormsMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AMRSEncounterFormsMappingService")
public class AMRSEncounterFormsMappingService {

    private AMRSEncountersFormMappingRepository amrsEncountersFormMappingRepository;
    @Autowired
    public AMRSEncounterFormsMappingService(AMRSEncountersFormMappingRepository amrsEncountersFormMappingRepository) {
        this.amrsEncountersFormMappingRepository = amrsEncountersFormMappingRepository;
    }
    public AMRSEncountersFormMappings save(AMRSEncountersFormMappings dataset){
        return amrsEncountersFormMappingRepository.save(dataset);
    }
    public List<AMRSEncountersFormMappings> findAll() {
        return amrsEncountersFormMappingRepository.findAll();
    }

    public AMRSEncountersFormMappings findByAmrsEncounterId(String encounterId) {
        return amrsEncountersFormMappingRepository.findByAmrsEncounterId(encounterId);
    }
}
