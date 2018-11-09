package com.example.hassaan.attendancekeeper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblAttendance {

    @SerializedName("AttendaceId")
    @Expose
    private Integer attendaceId;
    @SerializedName("CheckIn")
    @Expose
    private String checkIn;
    @SerializedName("CheckOut")
    @Expose
    private String checkOut;
    @SerializedName("TotalDuration")
    @Expose
    private String totalDuration;
    @SerializedName("EmployeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     */
    public TblAttendance() {
    }

    /**
     * @param updatedAt
     * @param employeeId
     * @param totalDuration
     * @param checkOut
     * @param createdAt
     * @param attendaceId
     * @param checkIn
     */
    public TblAttendance(Integer attendaceId, String checkIn, String checkOut, String totalDuration, Integer employeeId, String createdAt, String updatedAt) {
        super();
        this.attendaceId = attendaceId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalDuration = totalDuration;
        this.employeeId = employeeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getAttendaceId() {
        return attendaceId;
    }

    public void setAttendaceId(Integer attendaceId) {
        this.attendaceId = attendaceId;
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

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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
