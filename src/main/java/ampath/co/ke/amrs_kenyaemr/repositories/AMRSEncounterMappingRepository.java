package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncountersMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSEncounterMappingRepository extends JpaRepository<AMRSEncountersMapping, Long> {

    AMRSEncountersMapping findById(int pid);
    List<AMRSEncountersMapping> findByAmrsEncounterTypeId(String AMRS_Encounter_Type_ID);
    List<AMRSEncountersMapping> findByKenyaemrEncounterTypeId(String KenyaEMR_Encounter_Type_ID);
    List<AMRSEncountersMapping> findByKenyaemrEncounterTypeUuid(String KenyaEMR_Encounter_Type_UUID);

}
