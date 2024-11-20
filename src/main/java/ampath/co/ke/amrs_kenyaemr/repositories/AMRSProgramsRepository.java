package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPrograms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSProgramsRepository")
public interface AMRSProgramsRepository extends JpaRepository<AMRSPrograms, Long> {
    AMRSPrograms findById(int pid);
    List<AMRSPrograms> findByUUID(String uuid);
    List<AMRSPrograms> findByUUIDAndParentLocationUuid(String uuid,String location);
    List<AMRSPrograms> findByParentLocationUuid(String location);
    List<AMRSPrograms> findByPatientIdAndProgramID(String patient_id,int pid);
}
