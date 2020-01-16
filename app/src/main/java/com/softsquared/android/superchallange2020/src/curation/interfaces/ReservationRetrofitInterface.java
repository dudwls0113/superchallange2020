package com.softsquared.android.superchallange2020.src.curation.interfaces;

import com.softsquared.android.superchallange2020.src.curation.model.ReservationResponse;
import com.softsquared.android.superchallange2020.src.curation.model.VerifyResponse;
import com.softsquared.android.superchallange2020.src.main.models.DefaultResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReservationRetrofitInterface {
    @POST("/reservation")
    Call<ReservationResponse> postReservation(@Body RequestBody params);

    @POST("/soundRequest")
    Call<ReservationResponse> postSoundRequest(@Body RequestBody params);

    @POST("/isVerify")
    Call<VerifyResponse> getVerify(@Body RequestBody params);
}
