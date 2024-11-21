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

    private Integer AMRS_Encounter_Type_ID;

    private Integer KenyaEMR_Encounter_Type_ID;

    private String KenyaEMR_Encounter_Type_UUID;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getAMRS_Encounter_Type_ID() {
        return AMRS_Encounter_Type_ID;
    }

    public void setAMRS_Encounter_Type_ID(Integer AMRS_Encounter_Type_ID) {
        this.AMRS_Encounter_Type_ID = AMRS_Encounter_Type_ID;
    }

    public Integer getKenyaEMR_Encounter_Type_ID() {
        return KenyaEMR_Encounter_Type_ID;
    }

    public void setKenyaEMR_Encounter_Type_ID(Integer kenyaEMR_Encounter_Type_ID) {
        KenyaEMR_Encounter_Type_ID = kenyaEMR_Encounter_Type_ID;
    }

    public String getKenyaEMR_Encounter_Type_UUID() {
        return KenyaEMR_Encounter_Type_UUID;
    }

    public void setKenyaEMR_Encounter_Type_UUID(String kenyaEMR_Encounter_Type_UUID) {
        KenyaEMR_Encounter_Type_UUID = kenyaEMR_Encounter_Type_UUID;
    }
}
