package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSIdentifiers;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatientAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSPersonAttributesRepository")
public interface AMRSPersonAttributesRepository extends JpaRepository<AMRSPatientAttributes, Long> {
    AMRSPatientAttributes findById(int pid);
  //  List<AMRSPatientAttributes> findByUuid(String uuid);
    List<AMRSPatientAttributes> findByPatientId(String uuid);
    List<AMRSPatientAttributes> findByPatientIdAndPersonAttributeTypeId(String uuid,String ptype);
   // List<AMRSPatientAttributes> findByUuidAndParentlocationuuid(String uuid,String location);

}
