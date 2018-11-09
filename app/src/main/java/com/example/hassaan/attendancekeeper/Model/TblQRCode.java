package com.example.hassaan.attendancekeeper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblQRCode {

    @SerializedName("QR_CodeId")
    @Expose
    private Integer qRCodeId;
    @SerializedName("CodeString")
    @Expose
    private String codeString;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     */
    public TblQRCode() {
    }

    /**
     * @param updatedAt
     * @param createdAt
     * @param qRCodeId
     * @param codeString
     */
    public TblQRCode(Integer qRCodeId, String codeString, String createdAt, String updatedAt) {
        super();
        this.qRCodeId = qRCodeId;
        this.codeString = codeString;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getQRCodeId() {
        return qRCodeId;
    }

    public void setQRCodeId(Integer qRCodeId) {
        this.qRCodeId = qRCodeId;
    }

    public String getCodeString() {
        return codeString;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString;
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
