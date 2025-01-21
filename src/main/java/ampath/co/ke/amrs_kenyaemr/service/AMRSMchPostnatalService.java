package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSMchEnrollment;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMchPostnatal;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSMchEnrollmentRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSMchPostnatalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mchPostnatal")
public class AMRSMchPostnatalService {

    private AMRSMchPostnatalRepository amrsMchPostnatalRepository;

    @Autowired
    public AMRSMchPostnatalService(AMRSMchPostnatalRepository amrsMchPostnatalRepository) {
        this.amrsMchPostnatalRepository = amrsMchPostnatalRepository;
    }
    public List<AMRSMchPostnatal> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsMchPostnatalRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSMchPostnatal> findFirstByOrderByIdDesc() {
        return amrsMchPostnatalRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSMchPostnatal> findByResponseCodeIsNull() {
        return amrsMchPostnatalRepository.findByResponseCodeIsNull();
    }

    public List<AMRSMchPostnatal> findByPatientId(String pid) {
        return amrsMchPostnatalRepository.findByPatientId(pid);
    }

    public List<AMRSMchPostnatal> findByVisitId(String pid) {
        return amrsMchPostnatalRepository.findByVisitId(pid);
    }

    public AMRSMchPostnatal save(AMRSMchPostnatal dataset) {
        return amrsMchPostnatalRepository.save(dataset);
    }
}
