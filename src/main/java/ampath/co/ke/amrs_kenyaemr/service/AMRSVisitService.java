package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPrograms;
import ampath.co.ke.amrs_kenyaemr.models.AMRSUsers;
import ampath.co.ke.amrs_kenyaemr.models.AMRSVisits;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSUsersRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSVisitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("visitsService")
public class AMRSVisitService {
    private AMRSVisitsRepository amrsVisitsRepository;
    @Autowired
    public AMRSVisitService(AMRSVisitsRepository amrsVisitsRepository) {
        this.amrsVisitsRepository = amrsVisitsRepository;
    }

    public List<AMRSVisits> getAll() {
        return amrsVisitsRepository.findAll();
    } // findByVisitID
    public List<AMRSVisits> findByVisitID(String visitId) {
        return amrsVisitsRepository.findByVisitId(visitId);
    }

    public AMRSVisits save(AMRSVisits dataset) {
        return amrsVisitsRepository.save(dataset);
    }
    public List<AMRSVisits> findFirstByOrderByIdDesc() {
        return amrsVisitsRepository.findFirstByOrderByIdDesc();
    }
    public List<AMRSVisits> findByResponseCodeIsNull() {
        return amrsVisitsRepository.findByResponseCodeIsNull();
    }

}
