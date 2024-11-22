package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.repositories.AMRSRegimenSwitchRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSTriageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("AMRSRegimenSwitch")
public class AMRSRegimenSwitchService {

    Date nowDate = new Date();
    private AMRSRegimenSwitchRepository amrsRegimenSwitchRepository;

    @Autowired
    public AMRSRegimenSwitchService(AMRSRegimenSwitchRepository amrsRegimenSwitchRepository) {
        this.amrsRegimenSwitchRepository = amrsRegimenSwitchRepository;
    }
}
