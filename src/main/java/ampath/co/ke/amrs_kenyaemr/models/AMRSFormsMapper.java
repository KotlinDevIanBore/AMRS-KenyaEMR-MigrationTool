package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "formMappings")
@Entity
public class AMRSFormsMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "amrs_form_id")
    private String amrsFormId;
    @Column(name = "amrs_encounter_type_id")
    private String amrsEncounterTypeId;
    @Column(name = "amrs_form_uuid")
    private String amrsFormUuid;
    @Column(name = "kenyaemr_form_uuid")
    private String kenyaemrFormUuid;
    @Column(name = "amrs_migration_status")
    private boolean amrsMigrationStatus;
    @Column(name = "amrs_migration_error_description",nullable = false)
    private boolean amrsMigrationErrorDescription;
}
