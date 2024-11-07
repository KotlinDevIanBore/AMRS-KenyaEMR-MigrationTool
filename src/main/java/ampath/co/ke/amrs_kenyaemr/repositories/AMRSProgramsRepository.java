package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPrograms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSProgramsRepository")
public interface AMRSProgramsRepository extends JpaRepository<AMRSPrograms, Long> {
    AMRSPrograms findById(int pid);
    List<AMRSPrograms> findByUuid(String uuid);
    List<AMRSPrograms> findByUuidAndParentlocationuuid(String uuid,String location);
}
