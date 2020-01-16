package com.softsquared.android.superchallange2020.src.verify.interfaces;

public interface VerifyActivityView {

    void validateSuccess(String message);

    void validateFailure(String message);

    void signUpSuccess(String message);

    void signUpFailure(String message);

}
