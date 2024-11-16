package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.models.AMRSUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSPatientRepository")
public interface AMRSPatientsRespository extends JpaRepository<AMRSPatients, Long> {
    AMRSPatients findById(int pid);
    AMRSPatients findByPersonId(String pid);
    List<AMRSPatients> findByUuid(String uuid);
    List<AMRSPatients> findByUuidAndParentlocationuuid(String uuid,String location);
}
