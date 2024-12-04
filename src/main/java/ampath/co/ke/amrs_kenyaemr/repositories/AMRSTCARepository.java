package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSTcas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSTCARepository")
public interface AMRSTCARepository extends JpaRepository<AMRSTcas, Long> {
    List<AMRSTcas> findByUuid(String uuid);

    List<AMRSTcas> findByResponseCodeIsNull();


}
