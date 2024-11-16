package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSLocations;
import ampath.co.ke.amrs_kenyaemr.models.AMRSUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSUsersRepository")
public interface AMRSUsersRepository extends JpaRepository<AMRSUsers, Long> {
    AMRSUsers findById(int pid);
    List<AMRSUsers> findByUuid(String cuuid);
    List<AMRSUsers> findByUuidAndAmrsLocation(String uuid,String location);
}
