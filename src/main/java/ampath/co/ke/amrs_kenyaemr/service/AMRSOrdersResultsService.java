package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSOrdersResults;
import ampath.co.ke.amrs_kenyaemr.models.AMRSRegimenSwitch;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSOrdersResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("AMRSOrdersResults")
public class AMRSOrdersResultsService {
    Date nowDate = new Date();
    private AMRSOrdersResultsRepository amrsOrdersResultsRepository;

    @Autowired
    public AMRSOrdersResultsService(AMRSOrdersResultsRepository amrsOrdersResultsRepository) {
        this.amrsOrdersResultsRepository = amrsOrdersResultsRepository;
    }

    public List<AMRSOrdersResults> findByPatientId(String pid) {
        return amrsOrdersResultsRepository.findByPatientId(pid);
    }

    public List<AMRSOrdersResults> findFirstByOrderByIdDesc() {
        return amrsOrdersResultsRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSOrdersResults> findByResponseCodeIsNull() {
        return amrsOrdersResultsRepository.findByResponseCodeIsNull();
    }
    public AMRSOrdersResults save(AMRSOrdersResults dataset) {
        return amrsOrdersResultsRepository.save(dataset);
    }
}
