package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSObs;
import ampath.co.ke.amrs_kenyaemr.models.AMRSOrders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSObsRepository extends JpaRepository<AMRSObs, Long> {
    AMRSObs findById(int pid);
   // List<AMRSEncounters> findByPersonId(String pid);
    List<AMRSObs> findByUUID(String uuid);
    List<AMRSObs>findByPatientIdAndEncounterIDAndConceptId(String pid, String encountid, String concept);
    List<AMRSObs> findByResponseCodeIsNull();
}
