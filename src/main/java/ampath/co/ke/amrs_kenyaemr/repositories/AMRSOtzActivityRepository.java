package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSOtzActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSOtzActivityRepository extends JpaRepository<AMRSOtzActivity, Long> {
    List<AMRSOtzActivity> findByResponseCodeIsNull();

    List<AMRSOtzActivity> findByEncounterId(String encounterId);
    List<AMRSOtzActivity> findByVisitId(String visitId);
}
