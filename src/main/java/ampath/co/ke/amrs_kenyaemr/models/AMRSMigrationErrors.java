package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "amrs_migration_errors")
@Entity
public class AMRSMigrationErrors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "amrs_module")
    private String amrsModule;
    @Column(name = "amrs_error")
    private String amrsError;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmrsModule() {
        return amrsModule;
    }

    public void setAmrsModule(String amrsModule) {
        this.amrsModule = amrsModule;
    }

    public String getAmrsError() {
        return amrsError;
    }

    public void setAmrsError(String amrsError) {
        this.amrsError = amrsError;
    }
}
