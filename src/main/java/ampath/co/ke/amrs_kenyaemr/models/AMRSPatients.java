package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "patients")
@Entity
public class AMRSPatients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name="uuid")
    private String uuid;
    @Column(name="person_id")
    private String personId;
    @Column(name="given_name")
    private String given_name;
    @Column(name="family_name")
    private String family_name;
    @Column(name="middle_name")
    private String middle_name;
    @Column(name="gender")
    private String gender;
    @Column(name="birthdate")
    private String birthdate;
    @Column(name="address1")
    private String address1;
    @Column(name="county_district")
    private String county_district;
    @Column(name="address4")
    private String address4;
    @Column(name="address5")
    private String address5;
    @Column(name="address6")
    private String address6;
    @Column(name="dead")
    private String dead;
    @Column(name="birthdate_estimated")
    private String birthdate_estimated;
    @Column(name="voided")
    private String voided;
    @Column(name="location_id")
    private String location_id;

    @Column(name="kenyaemr_patient_id")
    private String kenyaemrpatientId;

    @Column(name="kenyaemr_patient_uuid")
    private String kenyaemrpatientUUID;
    @Column(name="name")
    private String name;

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



    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCounty_district() {
        return county_district;
    }

    public void setCounty_district(String county_district) {
        this.county_district = county_district;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public String getAddress6() {
        return address6;
    }

    public void setAddress6(String address6) {
        this.address6 = address6;
    }

    public String getDead() {
        return dead;
    }

    public void setDead(String dead) {
        this.dead = dead;
    }

    public String getBirthdate_estimated() {
        return birthdate_estimated;
    }

    public void setBirthdate_estimated(String birthdate_estimated) {
        this.birthdate_estimated = birthdate_estimated;
    }

    public String getVoided() {
        return voided;
    }

    public void setVoided(String voided) {
        this.voided = voided;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getParentlocationuuid() {
        return parentlocationuuid;
    }

    public void setParentlocationuuid(String parentlocationuuid) {
        this.parentlocationuuid = parentlocationuuid;
    }

    public String getKenyaemrpatientId() {
        return kenyaemrpatientId;
    }

    public void setKenyaemrpatientId(String kenyaemrpatientId) {
        this.kenyaemrpatientId = kenyaemrpatientId;
    }

    public String getKenyaemrpatientUUID() {
        return kenyaemrpatientUUID;
    }

    public void setKenyaemrpatientUUID(String kenyaemrpatientUUID) {
        this.kenyaemrpatientUUID = kenyaemrpatientUUID;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
