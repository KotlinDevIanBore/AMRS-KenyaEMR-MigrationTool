package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSTriage;
import ampath.co.ke.amrs_kenyaemr.models.AMRSVisits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSVisitRepository")
public interface AMRSVisitsRepository extends JpaRepository<AMRSVisits, Long> {
    List<AMRSVisits> findAll();
    List<AMRSVisits> findByVisitId(String visitId);
    List<AMRSVisits> findFirstByOrderByIdDesc();
    List<AMRSVisits> findByResponseCodeIsNull();
}
