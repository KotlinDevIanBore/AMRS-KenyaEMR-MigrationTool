package ampath.co.ke.amrs_kenyaemr.models;

import jakarta.persistence.*;

@Table(name = "locations")
@Entity
public class AMRSLocations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name="child_location_id")
    private String childlocationId;

    @Column(name="parent_location_id")
    private String parentlocationId;
    @Column(name="parent_location_name")
    private String parentlocationName;

    @Column(name = "mflcode")
    private String mflcode;
    @Column(name = "parent_uuid")
    private String puuid;

    @Column(name = "child_location_name")
    private String childlocationName;

    @Column(name = "child_uuid")
    private String cuuid;

    @Column(name = "kenyaemr_uuid")
    private String kuuid;

    @Column(name = "kenyaemr_location_id")
    private String kenyaemrlocationid;

    @Column(name = "status")
    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMflcode() {
        return mflcode;
    }

    public void setMflcode(String mflcode) {
        this.mflcode = mflcode;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getChildlocationName() {
        return childlocationName;
    }

    public void setChildlocationName(String childlocationName) {
        this.childlocationName = childlocationName;
    }

    public String getCuuid() {
        return cuuid;
    }

    public void setCuuid(String luuid) {
        this.cuuid = luuid;
    }

    public String getKuuid() {
        return kuuid;
    }

    public void setKuuid(String kuuid) {
        this.kuuid = kuuid;
    }

    public String getChildlocationId() {
        return childlocationId;
    }

    public void setChildlocationId(String childlocationId) {
        this.childlocationId = childlocationId;
    }

    public String getParentlocationId() {
        return parentlocationId;
    }

    public void setParentlocationId(String parentlocationId) {
        this.parentlocationId = parentlocationId;
    }

    public String getParentlocationName() {
        return parentlocationName;
    }

    public void setParentlocationName(String parentlocationName) {
        this.parentlocationName = parentlocationName;
    }

    public String getKenyaemrlocationid() {
        return kenyaemrlocationid;
    }

    public void setKenyaemrlocationid(String kenyaemrlocationid) {
        this.kenyaemrlocationid = kenyaemrlocationid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
