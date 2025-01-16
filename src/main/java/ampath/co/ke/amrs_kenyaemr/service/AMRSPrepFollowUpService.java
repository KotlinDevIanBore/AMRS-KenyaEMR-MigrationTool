package ampath.co.ke.amrs_kenyaemr.service;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPrepFollowUp;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPrepFollowUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("prepFollowUp")
public class AMRSPrepFollowUpService {

    private AMRSPrepFollowUpRepository amrsPrepFollowUpRepository;

    @Autowired
    public AMRSPrepFollowUpService(AMRSPrepFollowUpRepository amrsPrepFollowUpRepository) {
        this.amrsPrepFollowUpRepository = amrsPrepFollowUpRepository;
    }
    public List<AMRSPrepFollowUp> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsPrepFollowUpRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSPrepFollowUp> findFirstByOrderByIdDesc() {
        return amrsPrepFollowUpRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSPrepFollowUp> findByResponseCodeIsNull() {
        return amrsPrepFollowUpRepository.findByResponseCodeIsNull();
    }

    public List<AMRSPrepFollowUp> findByPatientId(String pid) {
        return amrsPrepFollowUpRepository.findByPatientId(pid);
    }

    public List<AMRSPrepFollowUp> findByVisitId(String pid) {
        return amrsPrepFollowUpRepository.findByVisitId(pid);
    }

    public AMRSPrepFollowUp save(AMRSPrepFollowUp dataset) {
        return amrsPrepFollowUpRepository.save(dataset);
    }
}
