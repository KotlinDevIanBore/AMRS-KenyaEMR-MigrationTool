package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSFormsMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSFormsMappingRepository")
public interface AMRSFormsMappingRepository extends JpaRepository<AMRSFormsMapper, Long> {
    List<AMRSFormsMapper> findByAmrsFormId(String amrsFormId);
    List<AMRSFormsMapper> findAll();
    List<AMRSFormsMapper> findByAmrsEncounterTypeId(String encounterTypeid);
    List<AMRSFormsMapper> findBykenyaemrFormUuid(String kenyaemrFormUuid);

}
