package com.example.hassaan.attendancekeeper.Event;

import com.example.hassaan.attendancekeeper.Model.TblEmployee;

public class EmployeeDetailEvent {
    private TblEmployee message;
    private int position;

    public EmployeeDetailEvent(TblEmployee message, int position) {
        this.message = message;
        this.position = position;
    }

    public TblEmployee getMessage() {
        return message;
    }

    public void setMessage(TblEmployee message) {
        this.message = message;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
