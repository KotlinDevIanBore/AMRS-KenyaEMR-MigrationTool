package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSConceptMapper;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMappings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSMappingRepository")
public interface AMRSMappingRepository extends JpaRepository<AMRSMappings, Long> {
    AMRSMappings findById(int pid);
    List<AMRSMappings> findByAmrsConceptId(String pid);
   // List<AMRSConceptMapper> findByAmrsConceptID(String pid);
}
