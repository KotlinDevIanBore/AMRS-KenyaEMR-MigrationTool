package ampath.co.ke.amrs_kenyaemr.service;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMchAntenatal;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSMchAntenatalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("mchAntenatal")
public class AMRSMchAntenatalService {

    private AMRSMchAntenatalRepository amrsMchAntenatalRepository;

    @Autowired
    public AMRSMchAntenatalService(AMRSMchAntenatalRepository amrsMchAntenatalRepository) {
        this.amrsMchAntenatalRepository = amrsMchAntenatalRepository;
    }
    public List<AMRSMchAntenatal> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsMchAntenatalRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSMchAntenatal> findFirstByOrderByIdDesc() {
        return amrsMchAntenatalRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSMchAntenatal> findByResponseCodeIsNull() {
        return amrsMchAntenatalRepository.findByResponseCodeIsNull();
    }

    public List<AMRSMchAntenatal> findByPatientId(String pid) {
        return amrsMchAntenatalRepository.findByPatientId(pid);
    }

    public List<AMRSMchAntenatal> findByVisitId(String pid) {
        return amrsMchAntenatalRepository.findByVisitId(pid);
    }

    public AMRSMchAntenatal save(AMRSMchAntenatal dataset) {
        return amrsMchAntenatalRepository.save(dataset);
    }
}
