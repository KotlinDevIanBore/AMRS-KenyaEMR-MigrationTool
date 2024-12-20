package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSRegimenSwitch;
import ampath.co.ke.amrs_kenyaemr.models.AMRSTriage;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSRegimenSwitchRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSTriageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("AMRSRegimenSwitch")
public class AMRSRegimenSwitchService {

    Date nowDate = new Date();
    private AMRSRegimenSwitchRepository amrsRegimenSwitchRepository;

    @Autowired
    public AMRSRegimenSwitchService(AMRSRegimenSwitchRepository amrsRegimenSwitchRepository) {
        this.amrsRegimenSwitchRepository = amrsRegimenSwitchRepository;
    }

    public List<AMRSRegimenSwitch> findByEncounterId(String eid) {
        return amrsRegimenSwitchRepository.findByEncounterId(eid);
    }

    public List<AMRSRegimenSwitch> findByPatientId(String eid) {
        return amrsRegimenSwitchRepository.findByPatientId(eid);
    }

    public List<AMRSRegimenSwitch> findFirstByOrderByIdDesc() {
        return amrsRegimenSwitchRepository.findFirstByOrderByIdDesc();
    }
    public List<AMRSRegimenSwitch> findByResponseCodeIsNull() {
        return amrsRegimenSwitchRepository.findByResponseCodeIsNull();
    }
    public AMRSRegimenSwitch save(AMRSRegimenSwitch dataset) {
        return amrsRegimenSwitchRepository.save(dataset);
    }
}
