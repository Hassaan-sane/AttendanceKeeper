package com.example.hassaan.attendancekeeper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblAdminxEmployeexAttendance {

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
    @SerializedName("TotalEmployee")
    @Expose
    private Integer totalEmployee;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("EmployeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("AttendanceId")
    @Expose
    private Integer attendanceId;
    @SerializedName("CheckIn")
    @Expose
    private String checkIn;
    @SerializedName("CheckOut")
    @Expose
    private String checkOut;
    @SerializedName("TotalDuration")
    @Expose
    private Long totalDuration;

    /**
     * No args constructor for use in serialization
     *
     */
    public TblAdminxEmployeexAttendance() {
    }

    public TblAdminxEmployeexAttendance(Integer adminId, String name, String username, String dOB, String number, String orgCodeId, Integer totalEmployee, Object createdAt, Object updatedAt, Integer employeeId, Integer attendanceId, String checkIn, String checkOut, Long totalDuration) {
        this.adminId = adminId;
        this.name = name;
        this.username = username;
        this.dOB = dOB;
        this.number = number;
        this.orgCodeId = orgCodeId;
        this.totalEmployee = totalEmployee;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.employeeId = employeeId;
        this.attendanceId = attendanceId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalDuration = totalDuration;
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

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public Long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Long totalDuration) {
        this.totalDuration = totalDuration;
    }
}