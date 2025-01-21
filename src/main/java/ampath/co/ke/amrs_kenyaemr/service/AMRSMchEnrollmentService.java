package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSMchEnrollment;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPrepInitial;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSMchEnrollmentRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPrepInitialRepositiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mchEnrollment")
public class AMRSMchEnrollmentService {

    private AMRSMchEnrollmentRepository amrsMchEnrollmentRepository;

    @Autowired
    public AMRSMchEnrollmentService(AMRSMchEnrollmentRepository amrsMchEnrollmentRepository) {
        this.amrsMchEnrollmentRepository = amrsMchEnrollmentRepository;
    }
    public List<AMRSMchEnrollment> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsMchEnrollmentRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSMchEnrollment> findFirstByOrderByIdDesc() {
        return amrsMchEnrollmentRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSMchEnrollment> findByResponseCodeIsNull() {
        return amrsMchEnrollmentRepository.findByResponseCodeIsNull();
    }

    public List<AMRSMchEnrollment> findByPatientId(String pid) {
        return amrsMchEnrollmentRepository.findByPatientId(pid);
    }

    public List<AMRSMchEnrollment> findByVisitId(String pid) {
        return amrsMchEnrollmentRepository.findByVisitId(pid);
    }

    public AMRSMchEnrollment save(AMRSMchEnrollment dataset) {
        return amrsMchEnrollmentRepository.save(dataset);
    }
}
