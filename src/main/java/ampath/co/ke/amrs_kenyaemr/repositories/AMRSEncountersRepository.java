package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncounters;
import ampath.co.ke.amrs_kenyaemr.models.AMRSEnrollments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSEncountersRepository extends JpaRepository<AMRSEncounters, Long> {
    AMRSEncounters findById(int pid);
   // List<AMRSEncounters> findByPersonId(String pid);
    List<AMRSEncounters> findByUUID(String uuid);
    List<AMRSEncounters>findByPatientIdAndEncounterIDAndConceptId(String pid,String encountid,String concept);
}
