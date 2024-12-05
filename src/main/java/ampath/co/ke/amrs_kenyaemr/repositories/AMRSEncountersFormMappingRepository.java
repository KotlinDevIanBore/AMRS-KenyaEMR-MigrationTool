package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncountersFormMappings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSEncountersFormMappingRepository")
public interface AMRSEncountersFormMappingRepository extends JpaRepository<AMRSEncountersFormMappings, Long> {
    AMRSEncountersFormMappings findByAmrsEncounterId(String amrsEncounterId);
    List<AMRSEncountersFormMappings> findAll();
}
