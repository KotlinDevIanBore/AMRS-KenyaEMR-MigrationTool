package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSConceptMapper;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMappings;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSConceptMappingRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("mappingService")
public class AMRSMappingService {
    Date nowDate = new Date();
    private AMRSMappingRepository amrsMappingRepository;
    @Autowired
    public AMRSMappingService(AMRSMappingRepository amrsMappingRepository) {
        this.amrsMappingRepository = amrsMappingRepository;
    }
    public List<AMRSMappings> getAll() {
        return amrsMappingRepository.findAll();
    }

    public AMRSMappings save(AMRSMappings dataset) {
        return amrsMappingRepository.save(dataset);
    }
    public List<AMRSMappings> findByAmrsConceptID(String pid) {
        return amrsMappingRepository.findByAmrsConceptId(pid);
    }



}
