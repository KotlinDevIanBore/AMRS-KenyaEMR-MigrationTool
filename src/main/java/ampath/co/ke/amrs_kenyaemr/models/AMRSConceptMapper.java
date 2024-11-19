package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "mappings")
@Entity
public class AMRSConceptMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "amrs_parent")
    private String amrsParent;
    @Column(name = "amrs_label")
    private String amrsLabel;
    @Column(name = "amrs_concept_uuid")
    private String amrsConceptUUID;
    @Column(name = "amrs_cocepts_id")
    private String amrsConceptID;
    @Column(name = "amrs_concept_type")
    private String amrsConceptType;
    @Column(name = "ciel")
    private String CIEL;
    @Column(name = "kenyaemr_parent")
    private String kenyaemrParent;
    @Column(name = "kenyaemr_label")
    private String kenyaemrLabel;
    @Column(name = "kenyaemr_concept_uuid")
    private String kenyaemrConceptUUID;
    @Column(name = "kenyaemr_concept_id")
    private String kenyaemrConceptID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmrsParent() {
        return amrsParent;
    }

    public void setAmrsParent(String amrsParent) {
        this.amrsParent = amrsParent;
    }

    public String getAmrsLabel() {
        return amrsLabel;
    }

    public void setAmrsLabel(String amrsLabel) {
        this.amrsLabel = amrsLabel;
    }

    public String getAmrsConceptUUID() {
        return amrsConceptUUID;
    }

    public void setAmrsConceptUUID(String amrsConceptUUID) {
        this.amrsConceptUUID = amrsConceptUUID;
    }

    public String getAmrsConceptID() {
        return amrsConceptID;
    }

    public void setAmrsConceptID(String amrsConceptID) {
        this.amrsConceptID = amrsConceptID;
    }

    public String getAmrsConceptType() {
        return amrsConceptType;
    }

    public void setAmrsConceptType(String amrsConceptType) {
        this.amrsConceptType = amrsConceptType;
    }

    public String getCIEL() {
        return CIEL;
    }

    public void setCIEL(String CIEL) {
        this.CIEL = CIEL;
    }

    public String getKenyaemrParent() {
        return kenyaemrParent;
    }

    public void setKenyaemrParent(String kenyaemrParent) {
        this.kenyaemrParent = kenyaemrParent;
    }

    public String getKenyaemrLabel() {
        return kenyaemrLabel;
    }

    public void setKenyaemrLabel(String kenyaemrLabel) {
        this.kenyaemrLabel = kenyaemrLabel;
    }

    public String getKenyaemrConceptUUID() {
        return kenyaemrConceptUUID;
    }

    public void setKenyaemrConceptUUID(String kenyaemrConceptUUID) {
        this.kenyaemrConceptUUID = kenyaemrConceptUUID;
    }

    public String getKenyaemrConceptID() {
        return kenyaemrConceptID;
    }

    public void setKenyaemrConceptID(String kenyaemrConceptID) {
        this.kenyaemrConceptID = kenyaemrConceptID;
    }
}