package com.softsquared.android.superchallange2020.src.seat_choice.interfaces;

import com.softsquared.android.superchallange2020.src.seat_choice.model.ChoiceResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ChoiceRetrofitInterface {

    @GET("/seat")
    Call<ChoiceResponse> getTest();

}
