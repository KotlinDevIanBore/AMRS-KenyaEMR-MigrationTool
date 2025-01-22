package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSMchDelivery;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMchEnrollment;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSMchDeliveryRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSMchEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mchDelivery")
public class AMRSMchDeliveryService {

    private AMRSMchDeliveryRepository amrsMchDeliveryRepository;

    @Autowired
    public AMRSMchDeliveryService(AMRSMchDeliveryRepository amrsMchDeliveryRepository) {
        this.amrsMchDeliveryRepository = amrsMchDeliveryRepository;
    }
    public List<AMRSMchDelivery> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsMchDeliveryRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSMchDelivery> findFirstByOrderByIdDesc() {
        return amrsMchDeliveryRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSMchDelivery> findByResponseCodeIsNull() {
        return amrsMchDeliveryRepository.findByResponseCodeIsNull();
    }

    public List<AMRSMchDelivery> findByPatientId(String pid) {
        return amrsMchDeliveryRepository.findByPatientId(pid);
    }

    public List<AMRSMchDelivery> findByVisitId(String pid) {
        return amrsMchDeliveryRepository.findByVisitId(pid);
    }

    public AMRSMchDelivery save(AMRSMchDelivery dataset) {
        return amrsMchDeliveryRepository.save(dataset);
    }
}
