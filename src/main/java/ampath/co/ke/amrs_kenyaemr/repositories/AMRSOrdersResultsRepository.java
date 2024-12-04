package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSOrdersResults;
import ampath.co.ke.amrs_kenyaemr.models.AMRSRegimenSwitch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSOrdersResultsRepository extends JpaRepository<AMRSOrdersResults, Long> {
    List<AMRSOrdersResults> findByPatientId(String pid);

    List<AMRSOrdersResults> findFirstByOrderByIdDesc();

    List<AMRSOrdersResults> findByResponseCodeIsNull();

}
