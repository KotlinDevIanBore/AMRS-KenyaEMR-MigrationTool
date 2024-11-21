package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncounters;
import ampath.co.ke.amrs_kenyaemr.models.AMRSVisits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSEncountersRepository extends JpaRepository<AMRSEncounters, Long> {
    AMRSEncounters findById(int pid);
    List<AMRSEncounters> findFirstByOrderByIdDesc();
    List<AMRSEncounters> findByPatientIdAndEncounterId(String pid, String encid);
    List<AMRSEncounters> findByResponseCodeIsNullOrderByIdAsc();

}
