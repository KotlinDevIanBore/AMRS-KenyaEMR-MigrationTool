package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSVisits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSVisitsRepository extends JpaRepository<AMRSVisits, Long> {

    List<AMRSVisits> findAll();
    List<AMRSVisits> findByvisitId(String visitId);
}
