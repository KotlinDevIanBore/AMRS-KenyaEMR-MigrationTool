package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSConceptMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSMappingRepository")
public interface AMRSMappingRepository extends JpaRepository<AMRSConceptMapper, Long> {
    AMRSConceptMapper findById(int pid);
    List<AMRSConceptMapper> findByAmrsConceptID(String pid);
   // List<AMRSConceptMapper> findByAmrsConceptID(String pid);
}
