package com.softsquared.android.superchallange2020.src.verify.interfaces;

import com.softsquared.android.superchallange2020.src.seat_choice.model.ChoiceResponse;
import com.softsquared.android.superchallange2020.src.verify.model.SignUpResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface VerifyRetrofitInterface {

    @POST("/lbUser")
    Call<SignUpResponse> postSignUp(@Body RequestBody params);

}
