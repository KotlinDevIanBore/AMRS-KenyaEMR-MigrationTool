package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "amrs_users")
@Entity
public class AMRSUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "system_id")
    private String system_id;
    @Column(name = "username")
    private String username;
    @Column(name = "given_name")
    private String given_name;
    @Column(name = "family_name")
    private String family_name;
    @Column(name = "middle_name")
    private String middle_name;
    @Column(name = "gender")
    private String gender;
    @Column(name = "birthdate")
    private String birthdate;
    @Column(name = "address1")
    private String address1;
    @Column(name = "county_district")
    private String county_district;
    @Column(name = "address4")
    private String address4;
    @Column(name = "address5")
    private String address5;
    @Column(name = "address6")
    private String address6;
    @Column(name = "kenyaemr_user_id")
    private String kenyaemr_user_id;
    @Column(name = "amrs_location")
    private String amrsLocation;
    @Column(name = "dead")
    private String dead;
    @Column(name = "birthdate_estimate")
    private String birthdate_estimate;
    @Column(name = "migrated")
    private int migrated;

    @Column(name = "response_code")
    private String response_code;


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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSystem_id() {
        return system_id;
    }

    public void setSystem_id(String system_id) {
        this.system_id = system_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getKenyaemr_user_id() {
        return kenyaemr_user_id;
    }

    public void setKenyaemr_user_id(String kenyaemr_user_id) {
        this.kenyaemr_user_id = kenyaemr_user_id;
    }

    public String getAmrsLocation() {
        return amrsLocation;
    }

    public void setAmrsLocation(String amrsLocation) {
        this.amrsLocation = amrsLocation;
    }

    public String getDead() {
        return dead;
    }

    public void setDead(String dead) {
        this.dead = dead;
    }

    public String getBirthdate_estimate() {
        return birthdate_estimate;
    }

    public void setBirthdate_estimate(String birthdate_estimate) {
        this.birthdate_estimate = birthdate_estimate;
    }

    public int getMigrated() {
        return migrated;
    }

    public void setMigrated(int migrated) {
        this.migrated = migrated;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }
}
