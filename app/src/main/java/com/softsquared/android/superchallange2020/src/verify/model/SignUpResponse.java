package com.softsquared.android.superchallange2020.src.verify.model;

import com.google.gson.annotations.SerializedName;
import com.softsquared.android.superchallange2020.src.seat_choice.model.Result;

import java.util.List;

public class SignUpResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}