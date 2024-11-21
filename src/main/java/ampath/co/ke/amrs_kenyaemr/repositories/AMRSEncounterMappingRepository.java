package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSEncountersMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AMRSEncounterMappingRepository extends JpaRepository<AMRSEncountersMapping, Long> {

    AMRSEncountersMapping findById(int pid);

    List<AMRSEncountersMapping> findByAMRSEncounterTypeID(int AMRS_Encounter_Type_ID);
    List<AMRSEncountersMapping> findByKenyaEMREncounterTypeID(int KenyaEMR_Encounter_Type_ID);
    List<AMRSEncountersMapping> findByKenyaEMREncounterTypeUUID(String KenyaEMR_Encounter_Type_UUID);

}
