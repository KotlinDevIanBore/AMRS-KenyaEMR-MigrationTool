package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.models.AMRSUsers;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPatientsRespository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("patientsService")
public class AMRSPatientServices {
    Date nowDate = new Date();
    private AMRSPatientsRespository amrsPatientsRespository;
    @Autowired
    public AMRSPatientServices(AMRSPatientsRespository amrsPatientsRespository) {
        this.amrsPatientsRespository = amrsPatientsRespository;
    }
    public List<AMRSPatients> getAll() {
        return amrsPatientsRespository.findAll();
    }

    public List<AMRSPatients> getByPatientID(String pid) {
        return amrsPatientsRespository.findByPersonId(pid);
    }

    public List<AMRSPatients> getPatientByLocation(String uuid,String location) {
        return amrsPatientsRespository.findByUuidAndParentlocationuuid(uuid, location);
    }
    public List<AMRSPatients> findFirstByOrderByIdDesc() {
        return amrsPatientsRespository.findFirstByOrderByIdDesc();
    }


    public List<AMRSPatients> getPatientByStatus(String uuid) {
        return amrsPatientsRespository.findByUuidAndResponseCodeIsNull(uuid);
    }
    public AMRSPatients save(AMRSPatients dataset) {
        return amrsPatientsRespository.save(dataset);
    }

}
