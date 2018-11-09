package com.example.hassaan.attendancekeeper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblUser {

    @SerializedName("AdminId")
    @Expose
    private Integer adminId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("Number")
    @Expose
    private String number;
    @SerializedName("OrgCodeId")
    @Expose
    private String orgCodeId;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("TotalEmployee")
    @Expose
    private Integer totalEmployee;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public TblUser() {
    }

    /**
     *
     * @param updatedAt
     * @param username
     * @param adminId
     * @param orgCodeId
     * @param createdAt
     * @param totalEmployee
     * @param name
     * @param number
     * @param dOB
     */


    public TblUser(Integer adminId, String name, String username, String dOB, String number, String orgCodeId, String userId, Integer totalEmployee, String createdAt, String updatedAt) {
        this.adminId = adminId;
        this.name = name;
        this.username = username;
        this.dOB = dOB;
        this.number = number;
        this.orgCodeId = orgCodeId;
        this.userId = userId;
        this.totalEmployee = totalEmployee;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrgCodeId() {
        return orgCodeId;
    }

    public void setOrgCodeId(String orgCodeId) {
        this.orgCodeId = orgCodeId;
    }

    public Integer getTotalEmployee() {
        return totalEmployee;
    }

    public void setTotalEmployee(Integer totalEmployee) {
        this.totalEmployee = totalEmployee;
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
