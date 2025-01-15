package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSIdentifiers;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSIdentifiersRepository")
public interface AMRSIdentifiersRepository extends JpaRepository<AMRSIdentifiers, Long> {
    AMRSIdentifiers findById(int pid);
    List<AMRSIdentifiers> findByUuid(String uuid);
    List<AMRSIdentifiers> findByPatientid(String uuid);
    List<AMRSIdentifiers> findByPatientidAndIdentifierType(String uuid,String type);
    List<AMRSIdentifiers> findByUuidAndParentlocationuuid(String uuid,String location);

}
