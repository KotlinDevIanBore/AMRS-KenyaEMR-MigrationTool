package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSPatientRelationship;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatientStatus;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPatientRelationshipRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPatientStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("amrsPatientRelationshipService")
public class AMRSPatientRelationshipService {

    Date nowDate = new Date();
    private AMRSPatientRelationshipRepository amrsPatientRelationshipRepository;

    @Autowired
    public AMRSPatientRelationshipService(AMRSPatientRelationshipRepository amrsPatientRelationshipRepository) {
        this.amrsPatientRelationshipRepository = amrsPatientRelationshipRepository;
    }

    public List<AMRSPatientRelationship> findByPersonId(String pid) {
        return amrsPatientRelationshipRepository.findByPersonA(pid);
    }

    public List<AMRSPatientRelationship> findFirstByOrderByIdDesc() {
        return amrsPatientRelationshipRepository.findFirstByOrderByIdDesc();
    }
    public List<AMRSPatientRelationship> findByResponseCodeIsNull() {
        return amrsPatientRelationshipRepository.findByResponseCodeIsNull();
    }

    public List<AMRSPatientRelationship> findByPersonB(String pid) {
        return amrsPatientRelationshipRepository.findByPersonB(pid);
    }


    public List<AMRSPatientRelationship> findByPersonAAndPeronBAndRelationship(String pa,String pb,String r) {
        return amrsPatientRelationshipRepository.findByPersonAAndPersonBAndRelationshipType(pa,pb,r);
    }

    public AMRSPatientRelationship save(AMRSPatientRelationship dataset) {
        return amrsPatientRelationshipRepository.save(dataset);
    }
}
