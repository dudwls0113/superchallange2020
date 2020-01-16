package com.softsquared.android.superchallange2020.src.curation;


import com.softsquared.android.superchallange2020.src.curation.interfaces.CurationActivityView;
import com.softsquared.android.superchallange2020.src.curation.interfaces.ReservationRetrofitInterface;
import com.softsquared.android.superchallange2020.src.curation.model.ReservationResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softsquared.android.superchallange2020.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.softsquared.android.superchallange2020.src.ApplicationClass.getRetrofit;

class CurationService {
    private final CurationActivityView mCurationActivityView;
    private JSONObject mParams;

    CurationService(final CurationActivityView curationActivityView, JSONObject params) {
        this.mCurationActivityView = curationActivityView;
        this.mParams = params;
    }

    void postReservation() {
        final ReservationRetrofitInterface reservationRetrofitInterface = getRetrofit().create(ReservationRetrofitInterface.class);
        reservationRetrofitInterface.postReservation(RequestBody.create(MEDIA_TYPE_JSON, mParams.toString())).enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                final ReservationResponse reservationResponse = response.body();
                if (reservationResponse == null) {
                    mCurationActivityView.reservationFailure(null);
                    return;
                }

                mCurationActivityView.reservationSuccess(reservationResponse.getMessage());
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                mCurationActivityView.reservationFailure(null);
            }
        });
    }



    void postSoundRequest() {
        final ReservationRetrofitInterface reservationRetrofitInterface = getRetrofit().create(ReservationRetrofitInterface.class);
        reservationRetrofitInterface.postSoundRequest(RequestBody.create(MEDIA_TYPE_JSON, mParams.toString())).enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                final ReservationResponse reservationResponse = response.body();
                if (reservationResponse == null) {
                    mCurationActivityView.reservationFailure(null);
                    return;
                }

                mCurationActivityView.reservationSuccess(reservationResponse.getMessage());
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                mCurationActivityView.reservationFailure(null);
            }
        });
    }


}
