package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "encountersMapping")
@Entity

public class AMRSEncountersMapping {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer Id;
    private String amrsEncounterTypeId;
    private String kenyaemrEncounterTypeId;
    private String kenyaemrEncounterTypeUuid;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getAmrsEncounterTypeId() {
        return amrsEncounterTypeId;
    }

    public void setAmrsEncounterTypeId(String amrsEncounterTypeId) {
        this.amrsEncounterTypeId = amrsEncounterTypeId;
    }

    public String getKenyaemrEncounterTypeId() {
        return kenyaemrEncounterTypeId;
    }

    public void setKenyaemrEncounterTypeId(String kenyaemrEncounterTypeId) {
        this.kenyaemrEncounterTypeId = kenyaemrEncounterTypeId;
    }

    public String getKenyaemrEncounterTypeUuid() {
        return kenyaemrEncounterTypeUuid;
    }

    public void setKenyaemrEncounterTypeUuid(String kenyaemrEncounterTypeUuid) {
        this.kenyaemrEncounterTypeUuid = kenyaemrEncounterTypeUuid;
    }
}
