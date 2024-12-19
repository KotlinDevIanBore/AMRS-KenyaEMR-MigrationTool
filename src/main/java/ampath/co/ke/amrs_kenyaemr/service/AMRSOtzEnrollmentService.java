package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSOtzEnrollment;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSOtzEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("amrsotzenrollmentservice")
public class AMRSOtzEnrollmentService {
    private final AMRSOtzEnrollmentRepository amrsOtzEnrollmentRepository;
    @Autowired
    public AMRSOtzEnrollmentService(AMRSOtzEnrollmentRepository amrsOtzEnrollmentRepository){
        this.amrsOtzEnrollmentRepository = amrsOtzEnrollmentRepository;
    }

    public List<AMRSOtzEnrollment> findall(){
        return amrsOtzEnrollmentRepository.findAll();
    }


    public List<AMRSOtzEnrollment> findByResponseCodeIsNull(){
        return amrsOtzEnrollmentRepository.findByResponseCodeIsNull();
    }

    public List<AMRSOtzEnrollment> findByEncounterId(String encounterId){
        return amrsOtzEnrollmentRepository.findByEncounterId(encounterId);
    }

    public List<AMRSOtzEnrollment> findByVisitId(String visitId){
        return amrsOtzEnrollmentRepository.findByVisitId(visitId);
    }

    public AMRSOtzEnrollment save (AMRSOtzEnrollment amrsOtzEnrollment){
        return amrsOtzEnrollmentRepository.save(amrsOtzEnrollment);
    }

    public List<AMRSOtzEnrollment> findByEncounterConceptAndPatient(String encounterId, String conceptId, String patientId) {
        return amrsOtzEnrollmentRepository.findByEncounterIdAndConceptIdAndPatientId(encounterId, conceptId, patientId);
    }
}
