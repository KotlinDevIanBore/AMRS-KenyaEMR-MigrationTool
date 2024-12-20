package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSArtRefill;
import ampath.co.ke.amrs_kenyaemr.models.AMRSHIVEnrollment;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSArtRefillRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSHIVEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AMRSArtRefill")
public class AMRSArtRefillService {

    private AMRSArtRefillRepository amrsArtRefillRepository;

    @Autowired
    public AMRSArtRefillService(AMRSArtRefillRepository amrsArtRefillRepository) {
        this.amrsArtRefillRepository = amrsArtRefillRepository;
    }
    public List<AMRSArtRefill> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsArtRefillRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSArtRefill> findFirstByOrderByIdDesc() {
        return amrsArtRefillRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSArtRefill> findByResponseCodeIsNull() {
        return amrsArtRefillRepository.findByResponseCodeIsNull();
    }

    public List<AMRSArtRefill> findByPatientId(String pid) {
        return amrsArtRefillRepository.findByPatientId(pid);
    }

    public List<AMRSArtRefill> findByVisitId(String pid) {
        return amrsArtRefillRepository.findByVisitId(pid);
    }

    public AMRSArtRefill save(AMRSArtRefill dataset) {
        return amrsArtRefillRepository.save(dataset);
    }
}
