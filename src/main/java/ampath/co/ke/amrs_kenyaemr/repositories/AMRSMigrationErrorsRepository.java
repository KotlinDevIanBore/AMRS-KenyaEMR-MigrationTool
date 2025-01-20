package ampath.co.ke.amrs_kenyaemr.repositories;

import ampath.co.ke.amrs_kenyaemr.models.AMRSMappings;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMigrationErrors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("AMRSMigrationErrorsRepository")
public interface AMRSMigrationErrorsRepository extends JpaRepository<AMRSMigrationErrors, Long> {
    AMRSMigrationErrors findById(int pid);
    List<AMRSMigrationErrors> findAll();
}