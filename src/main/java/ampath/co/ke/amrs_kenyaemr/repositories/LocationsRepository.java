package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSLocations;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LocationRepository")
public interface LocationsRepository extends JpaRepository<AMRSLocations, Long> {
    AMRSLocations findById(int pid);
    AMRSLocations findByCuuid(String cuuid);
    List<AMRSLocations> findByMflcode(String mflcode);
    List<AMRSLocations> findAll();
    List<AMRSLocations> findByPuuidAndStatus(String parent_uuid,int status);

   // @Query("SELECT p FROM PendullumData p WHERE date(p.encounterDate) = date(:encounterDate)")
   // List<AMRSLocations> getAllsummaries(String encounterDate, Pageable pageable);

    @Query("SELECT distinct(puuid)  FROM AMRSLocations where status=1")
    List<?> getParentLoactions();

}

