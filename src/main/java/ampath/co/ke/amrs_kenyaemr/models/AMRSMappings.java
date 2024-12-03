package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "concept_mappings")
@Entity
public class AMRSMappings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "amrs_concept_id")
    private String amrsConceptId;
    @Column(name = "kenyaemr_concept_uuid")
    private String kenyaemrConceptUuid;
    @Column(name = "kenyaemr_concept_id")
    private String kenyaemrConceptId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmrsConceptId() {
        return amrsConceptId;
    }

    public String getKenyaemrConceptUuid() {
        return kenyaemrConceptUuid;
    }

    public void setKenyaemrConceptUuid(String kenyaemrConceptUuid) {
        this.kenyaemrConceptUuid = kenyaemrConceptUuid;
    }

    public String getKenyaemrConceptId() {
        return kenyaemrConceptId;
    }

    public void setKenyaemrConceptId(String kenyaemrConceptId) {
        this.kenyaemrConceptId = kenyaemrConceptId;
    }

    public void setAmrsConceptId(String amrsConceptId) {
        this.amrsConceptId = amrsConceptId;
    }
}
