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
    @Column(name = "amrs_form_name")
    private String amrsFormName;
    @Column(name = "kenyaemr_form_uuid")
    private String kenyaemrFormUuid;
    @Column(name = "amrs_migration_error_description",nullable = true)
    private String amrsMigrationErrorDescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmrsFormId() {
        return amrsFormId;
    }

    public void setAmrsFormId(String amrsFormId) {
        this.amrsFormId = amrsFormId;
    }

    public String getAmrsEncounterTypeId() {
        return amrsEncounterTypeId;
    }

    public void setAmrsEncounterTypeId(String amrsEncounterTypeId) {
        this.amrsEncounterTypeId = amrsEncounterTypeId;
    }

    public String getAmrsFormName() {
        return amrsFormName;
    }

    public void setAmrsFormName(String amrsFormName) {
        this.amrsFormName = amrsFormName;
    }

    public String getKenyaemrFormUuid() {
        return kenyaemrFormUuid;
    }

    public void setKenyaemrFormUuid(String kenyaemrFormUuid) {
        this.kenyaemrFormUuid = kenyaemrFormUuid;
    }

    public String getAmrsMigrationErrorDescription() {
        return amrsMigrationErrorDescription;
    }

    public void setAmrsMigrationErrorDescription(String amrsMigrationErrorDescription) {
        this.amrsMigrationErrorDescription = amrsMigrationErrorDescription;
    }
}
