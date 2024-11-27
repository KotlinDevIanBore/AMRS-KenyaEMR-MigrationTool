package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEnrollments;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSEnrollmentsRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPatientsRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("enrollmentService")
public class AMRSEnrollmentService {
    Date nowDate = new Date();
    private AMRSEnrollmentsRepository amrsEnrollmentsRepository;
    @Autowired
    public AMRSEnrollmentService(AMRSEnrollmentsRepository amrsEnrollmentsRepository) {
        this.amrsEnrollmentsRepository = amrsEnrollmentsRepository;
    }
    public List<AMRSEnrollments> getAll() {
        return amrsEnrollmentsRepository.findAll();
    }

    public List<AMRSEnrollments> getByPatientID(String pid) {
        return amrsEnrollmentsRepository.findByPatientId(pid);
    }

    public void save(AMRSEnrollments ae) {
    }
}
