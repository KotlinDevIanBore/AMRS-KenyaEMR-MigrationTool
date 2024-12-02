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

}
