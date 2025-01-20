package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPrepInitial;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPrepMonthlyRefill;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPrepInitialRepositiory;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPrepMonthlyRefillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("AMRSPrepMonthlyRefill")
public class AMRSPrepMonthlyRefillService {

    private AMRSPrepMonthlyRefillRepository amrsPrepMonthlyRefillRepository;

    @Autowired
    public AMRSPrepMonthlyRefillService(AMRSPrepMonthlyRefillRepository amrsPrepMonthlyRefillRepository) {
        this.amrsPrepMonthlyRefillRepository = amrsPrepMonthlyRefillRepository;
    }
    public List<AMRSPrepMonthlyRefill> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsPrepMonthlyRefillRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSPrepMonthlyRefill> findFirstByOrderByIdDesc() {
        return amrsPrepMonthlyRefillRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSPrepMonthlyRefill> findByResponseCodeIsNull() {
        return amrsPrepMonthlyRefillRepository.findByResponseCodeIsNull();
    }

    public List<AMRSPrepMonthlyRefill> findByPatientId(String pid) {
        return amrsPrepMonthlyRefillRepository.findByPatientId(pid);
    }

    public List<AMRSPrepMonthlyRefill> findByVisitId(String pid) {
        return amrsPrepMonthlyRefillRepository.findByVisitId(pid);
    }

    public AMRSPrepMonthlyRefill save(AMRSPrepMonthlyRefill dataset) {
        return amrsPrepMonthlyRefillRepository.save(dataset);
    }
}
