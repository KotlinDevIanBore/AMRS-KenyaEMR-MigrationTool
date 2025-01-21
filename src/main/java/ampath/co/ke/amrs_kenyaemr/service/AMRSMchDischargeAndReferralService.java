package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSMchDischargeAndReferral;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSMchDischargeAndReferralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mchDischargeAndReferral")
public class AMRSMchDischargeAndReferralService {

    private AMRSMchDischargeAndReferralRepository amrsMchDischargeAndReferralRepository;

    @Autowired
    public AMRSMchDischargeAndReferralService(AMRSMchDischargeAndReferralRepository amrsMchDischargeAndReferralRepository) {
        this.amrsMchDischargeAndReferralRepository = amrsMchDischargeAndReferralRepository;
    }
    public List<AMRSMchDischargeAndReferral> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsMchDischargeAndReferralRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSMchDischargeAndReferral> findFirstByOrderByIdDesc() {
        return amrsMchDischargeAndReferralRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSMchDischargeAndReferral> findByResponseCodeIsNull() {
        return amrsMchDischargeAndReferralRepository.findByResponseCodeIsNull();
    }

    public List<AMRSMchDischargeAndReferral> findByPatientId(String pid) {
        return amrsMchDischargeAndReferralRepository.findByPatientId(pid);
    }

    public List<AMRSMchDischargeAndReferral> findByVisitId(String pid) {
        return amrsMchDischargeAndReferralRepository.findByVisitId(pid);
    }

    public AMRSMchDischargeAndReferral save(AMRSMchDischargeAndReferral dataset) {
        return amrsMchDischargeAndReferralRepository.save(dataset);
    }
}
