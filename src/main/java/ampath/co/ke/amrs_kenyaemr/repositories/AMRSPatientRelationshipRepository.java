package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPatientRelationship;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSPatientRelationshipRepository")
public interface AMRSPatientRelationshipRepository extends JpaRepository<AMRSPatientRelationship, Long> {
    List<AMRSPatientRelationship> findByPersonA(String pid);

    List<AMRSPatientRelationship> findByPersonB(String pid);
    List<AMRSPatientRelationship> findByPersonAAndPersonBAndRelationshipType(String pa,String pb,String r);

    List<AMRSPatientRelationship> findFirstByOrderByIdDesc();

    List<AMRSPatientRelationship> findByResponseCodeIsNull();
 }
