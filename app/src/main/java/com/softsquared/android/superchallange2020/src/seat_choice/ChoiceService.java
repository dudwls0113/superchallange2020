package com.softsquared.android.superchallange2020.src.seat_choice;

import com.softsquared.android.superchallange2020.src.main.interfaces.MainRetrofitInterface;
import com.softsquared.android.superchallange2020.src.main.models.DefaultResponse;
import com.softsquared.android.superchallange2020.src.seat_choice.interfaces.ChoiceActivityView;
import com.softsquared.android.superchallange2020.src.seat_choice.interfaces.ChoiceRetrofitInterface;
import com.softsquared.android.superchallange2020.src.seat_choice.model.ChoiceResponse;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softsquared.android.superchallange2020.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.softsquared.android.superchallange2020.src.ApplicationClass.getRetrofit;

class ChoiceService {
    private final ChoiceActivityView mChoiceActivityView;

    ChoiceService(final ChoiceActivityView choiceActivityView) {
        this.mChoiceActivityView = choiceActivityView;
    }

    void tryGetSeat() {
        final ChoiceRetrofitInterface choiceRetrofitInterface = getRetrofit().create(ChoiceRetrofitInterface.class);
        choiceRetrofitInterface.getTest().enqueue(new Callback<ChoiceResponse>() {
            @Override
            public void onResponse(Call<ChoiceResponse> call, Response<ChoiceResponse> response) {
                final ChoiceResponse choiceResponse = response.body();
                if (choiceResponse == null) {
                    mChoiceActivityView.getSeatFailure(null);
                    return;
                }

                mChoiceActivityView.getSeatSuccess(choiceResponse.getResults());
            }

            @Override
            public void onFailure(Call<ChoiceResponse> call, Throwable t) {
                mChoiceActivityView.getSeatFailure(null);
            }
        });
    }

    void reservationRequest(int seatNo) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("seatNo", seatNo);


        final ChoiceRetrofitInterface choiceRetrofitInterface = getRetrofit().create(ChoiceRetrofitInterface.class);
        choiceRetrofitInterface.reservationRequest(RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                final DefaultResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mChoiceActivityView.getSeatFailure(null);
                    return;
                }

                mChoiceActivityView.reservationRequsetSuccess();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                mChoiceActivityView.getSeatFailure(null);
            }
        });
    }
}
