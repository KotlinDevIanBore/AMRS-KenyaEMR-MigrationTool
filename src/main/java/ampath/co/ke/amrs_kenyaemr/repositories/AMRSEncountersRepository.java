package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncounters;
import ampath.co.ke.amrs_kenyaemr.models.AMRSEnrollments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AMRSEncountersRepository extends JpaRepository<AMRSEncounters, Long> {
    AMRSEncounters findById(int pid);
}
