package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSHIVEnrollment;
import ampath.co.ke.amrs_kenyaemr.models.AMRSTriage;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSHIVEnrollmentRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSTriageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("AMRSHIVEnrollmentService")
public class AMRSHIVEnrollmentService {

    Date nowDate = new Date();
    private AMRSHIVEnrollmentRepository amrsHIVEnrollmentRepository;

    @Autowired
    public AMRSHIVEnrollmentService(AMRSHIVEnrollmentRepository amrsHIVEnrollmentRepository) {
        this.amrsHIVEnrollmentRepository = amrsHIVEnrollmentRepository;
    }
    public List<AMRSHIVEnrollment> findByPatientIdAndEncounterIDAndConceptId(String pid, String eid, String cid) {
        return amrsHIVEnrollmentRepository.findByPatientIdAndEncounterIDAndConceptId(pid,eid,cid);
    }

    public List<AMRSHIVEnrollment> findFirstByOrderByIdDesc() {
        return amrsHIVEnrollmentRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSHIVEnrollment> findByResponseCodeIsNull() {
        return amrsHIVEnrollmentRepository.findByResponseCodeIsNull();
    }

    public List<AMRSHIVEnrollment> findByPatientId(String pid) {
        return amrsHIVEnrollmentRepository.findByPatientId(pid);
    }
    public List<AMRSHIVEnrollment> findByVisitId(String pid) {
        return amrsHIVEnrollmentRepository.findByVisitId(pid);
    }


    public AMRSHIVEnrollment save(AMRSHIVEnrollment dataset) {
        return amrsHIVEnrollmentRepository.save(dataset);
    }

}