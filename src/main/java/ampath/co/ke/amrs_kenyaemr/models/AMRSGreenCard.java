package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "greencard")
@Entity
public class AMRSGreenCard {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer Id;
    private String locationUuid;
    private String patientId;
    private String encounterId;
    private String encounterDateTime;
    private String encounterType;
    private String conceptId;
    private String conceptName;
    private String conceptValue;
    private String obsDateTime;
    private String value;
    private String valueDataType;
    private String dataTypeId;
    private String encounterName;
    private String Category;
}
