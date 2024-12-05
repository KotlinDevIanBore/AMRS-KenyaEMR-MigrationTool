package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncountersMapping;
import ampath.co.ke.amrs_kenyaemr.models.AMRSFormsMapper;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSConceptMappingRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSFormsMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AMRSFormsMappingService")
public class AMRSFormsMappingService {

    private AMRSFormsMappingRepository amrsFormsMappingRepository;
    @Autowired
    public AMRSFormsMappingService(AMRSFormsMappingRepository amrsMappingRepository) {
        this.amrsFormsMappingRepository = amrsMappingRepository;
    }
    public AMRSFormsMapper save(AMRSFormsMapper dataset){
        return amrsFormsMappingRepository.save(dataset);
    }
    public List<AMRSFormsMapper> findByAmrsFormId(String FormId) {
        return amrsFormsMappingRepository.findByAmrsFormId(FormId);
    }
    public List<AMRSFormsMapper> findByAmrsFormIdAndEncounterTypeId(String FormId, String encounterTypeId) {
        return amrsFormsMappingRepository.findByAmrsFormIdAndAmrsEncounterTypeId(FormId, encounterTypeId);
    }
    public List<AMRSFormsMapper> getAll() {
        return amrsFormsMappingRepository.findAll();
    }
    public List<AMRSFormsMapper> findBykenyaemrFormUuid(String kenyaemrFormUuid) {
        return amrsFormsMappingRepository.findBykenyaemrFormUuid(kenyaemrFormUuid);
    }
    public List<AMRSFormsMapper> findByAmrsEncounterTypeId(String encounterTypeId) {
        return amrsFormsMappingRepository.findByAmrsEncounterTypeId(encounterTypeId);
    }
}
