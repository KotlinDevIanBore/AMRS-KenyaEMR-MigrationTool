package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPrograms;
import ampath.co.ke.amrs_kenyaemr.models.AMRSUsers;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSProgramsRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("AMRSProgramService")
public class AMRSProgramService {
    Date nowDate = new Date();
    private AMRSProgramsRepository amrsProgramsRepository;
    @Autowired
    public AMRSProgramService(AMRSProgramsRepository amrsProgramsRepository) {
        this.amrsProgramsRepository = amrsProgramsRepository;
    }
    public List<AMRSPrograms> getAll() {
        return amrsProgramsRepository.findAll();
    }

    public List<AMRSPrograms> getprogramByLocation(String uuid,String location) {
        return amrsProgramsRepository.findByUUIDAndParentLocationUuid(uuid, location);
    }
    public List<AMRSPrograms> findByPatientIdAndProgramID(String pateint_id,int pid) {
        return amrsProgramsRepository.findByPatientIdAndProgramID(pateint_id, pid);
    }
    public List<AMRSPrograms> findByParentLocationUuid(String location) {
        return amrsProgramsRepository.findByParentLocationUuid(location);
    }


    public AMRSPrograms save(AMRSPrograms dataset) {
        return amrsProgramsRepository.save(dataset);
    }
}
