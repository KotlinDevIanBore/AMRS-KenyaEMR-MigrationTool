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
    public AMRSHIVEnrollmentService(AMRSHIVEnrollmentRepository AMRSHIVEnrollmentRepository) {
        this.amrsHIVEnrollmentRepository = amrsHIVEnrollmentRepository;
    }
    public List<AMRSHIVEnrollment> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsHIVEnrollmentRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSHIVEnrollment> findFirstByOrderByIdDesc() {
        return amrsHIVEnrollmentRepository.findFirstByOrderByIdDesc();
    }

    public AMRSHIVEnrollment save(AMRSHIVEnrollment dataset) {
        return amrsHIVEnrollmentRepository.save(dataset);
    }

}