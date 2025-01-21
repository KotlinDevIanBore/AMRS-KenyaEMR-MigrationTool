package ampath.co.ke.amrs_kenyaemr.repositories;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMchDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSMchDeliveryRepository extends JpaRepository<AMRSMchDelivery, Long> {

    List<AMRSMchDelivery> findAll();
    List<AMRSMchDelivery> findByPatientIdAndEncounterIdAndConceptId(String pid, String encid, String cid);
    List<AMRSMchDelivery> findFirstByOrderByIdDesc();
    List<AMRSMchDelivery> findByResponseCodeIsNull();
    List<AMRSMchDelivery> findByPatientId(String pid);
    List<AMRSMchDelivery> findByVisitId(String vid);
}
