package com.softsquared.android.superchallange2020.src.seat_choice.model;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("seatNo")
    private int seatNo;

    @SerializedName("status")
    private int status;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }
}