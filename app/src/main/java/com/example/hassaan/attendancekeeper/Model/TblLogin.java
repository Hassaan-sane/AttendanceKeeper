package com.example.hassaan.attendancekeeper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblLogin {

    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     */
    public TblLogin() {
    }

    /**
     * @param data
     */
    public TblLogin(Data data) {
        super();
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}


