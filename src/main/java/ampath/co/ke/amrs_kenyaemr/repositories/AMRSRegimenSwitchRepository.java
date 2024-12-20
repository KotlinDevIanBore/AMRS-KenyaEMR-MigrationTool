package ampath.co.ke.amrs_kenyaemr.repositories;


import ampath.co.ke.amrs_kenyaemr.models.AMRSRegimenSwitch;
import ampath.co.ke.amrs_kenyaemr.models.AMRSTriage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSRegimenSwitchRepository")
public interface AMRSRegimenSwitchRepository extends JpaRepository<AMRSRegimenSwitch, Long> {
    List<AMRSRegimenSwitch> findByEncounterId(String eid);
    List<AMRSRegimenSwitch> findByPatientId(String pid);

    List<AMRSRegimenSwitch> findFirstByOrderByIdDesc();

    List<AMRSRegimenSwitch> findByResponseCodeIsNull();
}
