package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncountersMapping;
import ampath.co.ke.amrs_kenyaemr.models.AMRSFormsMapper;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSFormsMappingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AMRSFormsMappingService")
public class AMRSFormsMappingService {
    private AMRSFormsMappingRepository amrsFormsMappingRepository;

    public AMRSFormsMapper save(AMRSFormsMapper dataset){
        return amrsFormsMappingRepository.save(dataset);
    }
    public AMRSFormsMapper findByAmrsFormId(String FormId) {
        return amrsFormsMappingRepository.findByAmrsFormId(FormId);
    }
    public List<AMRSFormsMapper> getAll() {
        return amrsFormsMappingRepository.getAll();
    }
    public List<AMRSFormsMapper> findBykenyaemrFormUuid(String kenyaemrFormUuid) {
        return amrsFormsMappingRepository.findBykenyaemrFormUuid(kenyaemrFormUuid);
    }
    public List<AMRSFormsMapper> findByAmrsEncounterTypeId(String encounterTypeId) {
        return amrsFormsMappingRepository.findByAmrsEncounterTypeId(encounterTypeId);
    }
    public List<AMRSFormsMapper> findByAmrsMigrationStatus(boolean migrationStatus) {
        return amrsFormsMappingRepository.findByAmrsMigrationStatus(migrationStatus);
    }
}
