package com.softsquared.android.superchallange2020.src.curation.model;

import com.google.gson.annotations.SerializedName;

public class VerifyResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("isVerify")
    boolean isVerify;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public boolean isVerify() {
        return isVerify;
    }
}