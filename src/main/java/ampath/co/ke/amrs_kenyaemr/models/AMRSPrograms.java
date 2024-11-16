package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "programs")
@Entity
public class AMRSPrograms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name="uuid")
    private String uuid;
    @Column(name="patientid")
    private String patientid;

    @Column(name="patientkenyaemruuid")
    private String patientkenyaemruuid;
    @Column(name="kenyaemruuid")
    private String kenyaemruuid;
    @Column(name="programname")
    private String programname;
    @Column(name="locationid")
    private String locationid;
    @Column(name="conceptid")
    private String conceptid;
    @Column(name="dateenrolled")
    private String dateenrolled;
    @Column(name="datecompleted")
    private String datecompleted;
    @Column(name = "parent_location_uuid")
    private String parentlocationuuid;
    @Column(name = "migrated")
    private int migrated;

    @Column(name = "response_code")
    private int response_code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getKenyaemruuid() {
        return kenyaemruuid;
    }

    public void setKenyaemruuid(String kenyaemruuid) {
        this.kenyaemruuid = kenyaemruuid;
    }

    public String getProgramname() {
        return programname;
    }

    public void setProgramname(String programname) {
        this.programname = programname;
    }

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getConceptid() {
        return conceptid;
    }

    public void setConceptid(String conceptid) {
        this.conceptid = conceptid;
    }

    public String getDateenrolled() {
        return dateenrolled;
    }

    public void setDateenrolled(String dateenrolled) {
        this.dateenrolled = dateenrolled;
    }

    public String getParentlocationuuid() {
        return parentlocationuuid;
    }

    public void setParentlocationuuid(String parentlocationuuid) {
        this.parentlocationuuid = parentlocationuuid;
    }

    public int getMigrated() {
        return migrated;
    }

    public void setMigrated(int migrated) {
        this.migrated = migrated;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getDatecompleted() {
        return datecompleted;
    }

    public void setDatecompleted(String datecompleted) {
        this.datecompleted = datecompleted;
    }

    public String getPatientkenyaemruuid() {
        return patientkenyaemruuid;
    }

    public void setPatientkenyaemruuid(String patientkenyaemruuid) {
        this.patientkenyaemruuid = patientkenyaemruuid;
    }
}
