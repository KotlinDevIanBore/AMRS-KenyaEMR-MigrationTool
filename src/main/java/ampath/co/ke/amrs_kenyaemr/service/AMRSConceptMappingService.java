package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSConceptMapper;
import ampath.co.ke.amrs_kenyaemr.models.AMRSEncounters;
import ampath.co.ke.amrs_kenyaemr.models.AMRSEnrollments;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSEnrollmentsRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("conceptService")
public class AMRSConceptMappingService {
    Date nowDate = new Date();
    private AMRSMappingRepository amrsMappingRepository;
    @Autowired
    public AMRSConceptMappingService(AMRSMappingRepository amrsMappingRepository) {
        this.amrsMappingRepository = amrsMappingRepository;
    }
    public List<AMRSConceptMapper> getAll() {
        return amrsMappingRepository.findAll();
    }

    public AMRSConceptMapper save(AMRSConceptMapper dataset) {
        return amrsMappingRepository.save(dataset);
    }
    public List<AMRSConceptMapper> findByAmrsConceptID(String pid) {
        return amrsMappingRepository.findByAmrsConceptID(pid);
    }



}
