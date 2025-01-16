package ampath.co.ke.amrs_kenyaemr.service;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPrepInitial;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPrepInitialRepositiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AMRSPrepInitial")
public class AMRSPrepInitialService {

    private AMRSPrepInitialRepositiory amrsPrepInitialRepositiory;

    @Autowired
    public AMRSPrepInitialService(AMRSPrepInitialRepositiory amrsPrepInitialRepositiory) {
        this.amrsPrepInitialRepositiory = amrsPrepInitialRepositiory;
    }
    public List<AMRSPrepInitial> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsPrepInitialRepositiory.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSPrepInitial> findFirstByOrderByIdDesc() {
        return amrsPrepInitialRepositiory.findFirstByOrderByIdDesc();
    }

    public List<AMRSPrepInitial> findByResponseCodeIsNull() {
        return amrsPrepInitialRepositiory.findByResponseCodeIsNull();
    }

    public List<AMRSPrepInitial> findByPatientId(String pid) {
        return amrsPrepInitialRepositiory.findByPatientId(pid);
    }

    public List<AMRSPrepInitial> findByVisitId(String pid) {
        return amrsPrepInitialRepositiory.findByVisitId(pid);
    }

    public AMRSPrepInitial save(AMRSPrepInitial dataset) {
        return amrsPrepInitialRepositiory.save(dataset);
    }
}
