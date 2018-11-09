package com.example.hassaan.attendancekeeper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblOrgCode {

    @SerializedName("CodeId")
    @Expose
    private Integer codeId;
    @SerializedName("Org_code")
    @Expose
    private String orgCode;
    @SerializedName("Org_name")
    @Expose
    private String orgName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     */
    public TblOrgCode() {
    }

    /**
     * @param updatedAt
     * @param codeId
     * @param address
     * @param createdAt
     * @param orgCode
     * @param orgName
     * @param country
     */
    public TblOrgCode(Integer codeId, String orgCode, String orgName, String address, String country, String createdAt, String updatedAt) {
        super();
        this.codeId = codeId;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.address = address;
        this.country = country;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}