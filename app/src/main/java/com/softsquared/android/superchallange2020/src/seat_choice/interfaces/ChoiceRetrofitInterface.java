package com.softsquared.android.superchallange2020.src.seat_choice.interfaces;

import com.softsquared.android.superchallange2020.src.main.models.DefaultResponse;
import com.softsquared.android.superchallange2020.src.seat_choice.model.ChoiceResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChoiceRetrofitInterface {

    @GET("/seat")
    Call<ChoiceResponse> getTest();

    @POST("/reservationRequest")
    Call<DefaultResponse> reservationRequest(@Body RequestBody params);
}
