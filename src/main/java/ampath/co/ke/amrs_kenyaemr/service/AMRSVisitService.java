package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSUsers;
import ampath.co.ke.amrs_kenyaemr.models.AMRSVisits;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSVisitsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("visitsService")
public class AMRSVisitService {
    private AMRSVisitsRepository amrsVisitsRepository;
    public List<AMRSVisits> getAll() {
        return amrsVisitsRepository.findAll();
    } // findByVisitID
    public List<AMRSVisits> findByVisitID(String visitId) {
        return amrsVisitsRepository.findByvisitId(visitId);
    }

    public AMRSVisits save(AMRSVisits dataset) {
        return amrsVisitsRepository.save(dataset);
    }
}
