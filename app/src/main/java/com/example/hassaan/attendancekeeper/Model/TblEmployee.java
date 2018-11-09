package com.example.hassaan.attendancekeeper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblEmployee {

    @SerializedName("EmployeeId")
    @Expose
    private Integer employeeId;
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
    @SerializedName("AdminId")
    @Expose
    private Integer adminId;
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
    public TblEmployee() {
    }

    /**
     *
     * @param updatedAt
     * @param employeeId
     * @param adminId
     * @param username
     * @param createdAt
     * @param name
     * @param number
     * @param dOB
     */
    public TblEmployee(Integer employeeId, String name, String username, String dOB, String number, Integer adminId, String createdAt, String updatedAt) {
        super();
        this.employeeId = employeeId;
        this.name = name;
        this.username = username;
        this.dOB = dOB;
        this.number = number;
        this.adminId = adminId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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