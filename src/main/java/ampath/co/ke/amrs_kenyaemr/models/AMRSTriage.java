package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "triage")
@Entity
public class AMRSTriage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)

    private String patientId;
    private String encounterID;
    private String encounterDateTime;

}
