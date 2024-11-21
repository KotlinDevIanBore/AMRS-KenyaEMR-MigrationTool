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

    private Integer amrsEncounterTypeId;


    private Integer kenyaemrEncounterTypeId;

    private String kenyaemrEncounterTypeUuid;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getAmrsEncounterTypeId() {
        return amrsEncounterTypeId;
    }

    public void setAmrsEncounterTypeId(Integer amrsEncounterTypeId) {
        this.amrsEncounterTypeId = amrsEncounterTypeId;
    }

    public Integer getKenyaemrEncounterTypeId() {
        return kenyaemrEncounterTypeId;
    }

    public void setKenyaemrEncounterTypeId(Integer kenyaemrEncounterTypeId) {
        this.kenyaemrEncounterTypeId = kenyaemrEncounterTypeId;
    }

    public String getKenyaemrEncounterTypeUuid() {
        return kenyaemrEncounterTypeUuid;
    }

    public void setKenyaemrEncounterTypeUuid(String kenyaemrEncounterTypeUuid) {
        this.kenyaemrEncounterTypeUuid = kenyaemrEncounterTypeUuid;
    }
}
