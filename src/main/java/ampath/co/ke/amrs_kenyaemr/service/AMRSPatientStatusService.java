package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSPatientStatus;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPatientStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("amrsPatientStatusService")
public class AMRSPatientStatusService {

    Date nowDate = new Date();
    private AMRSPatientStatusRepository amrsPatientStatusRepository;

    @Autowired
    public AMRSPatientStatusService(AMRSPatientStatusRepository amrsPatientStatusRepository) {
        this.amrsPatientStatusRepository = amrsPatientStatusRepository;
    }

    public List<AMRSPatientStatus> findByPersonId(String pid) {
        return amrsPatientStatusRepository.findByPersonId(pid);
    }

    public List<AMRSPatientStatus> findFirstByOrderByIdDesc() {
        return amrsPatientStatusRepository.findFirstByOrderByIdDesc();
    }
    public List<AMRSPatientStatus> findByResponseCodeIsNull() {
        return amrsPatientStatusRepository.findByResponseCodeIsNull();
    }
    public AMRSPatientStatus save(AMRSPatientStatus dataset) {
        return amrsPatientStatusRepository.save(dataset);
    }
}
