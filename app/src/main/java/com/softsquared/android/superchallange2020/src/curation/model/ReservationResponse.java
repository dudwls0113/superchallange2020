package com.softsquared.android.superchallange2020.src.curation.model;

import com.google.gson.annotations.SerializedName;

public class ReservationResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}