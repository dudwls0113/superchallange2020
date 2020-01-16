package com.softsquared.android.superchallange2020.src.seat_choice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.BaseActivity;
import com.softsquared.android.superchallange2020.src.seat_choice.interfaces.ChoiceActivityView;
import com.softsquared.android.superchallange2020.src.seat_choice.model.Result;

import org.json.JSONException;

import java.util.List;

public class SeatChoiceActivity extends BaseActivity implements ChoiceActivityView {

    ImageView mIvSeat1;
    ImageView mIvSeat2;

    Spinner spinner;
    Context mContext;
    String[] strings = {"1-1", "1-2", "1-3", "1-4"};


    int seat1, seat2, seat3, seat4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_choice);
        mContext = this;

        mIvSeat1 = findViewById(R.id.activity_seat_choice_iv_seat1);
        mIvSeat2 = findViewById(R.id.activity_seat_choice_iv_seat4);

        spinner = findViewById(R.id.activity_seat_choice_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.custom_spinner, strings);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        final ChoiceService choiceService = new ChoiceService(this);
        choiceService.tryGetSeat();

    }

    public void reservationRequest(int seatNo) throws JSONException {
        showProgressDialog();
        final ChoiceService choiceService = new ChoiceService(this);
        choiceService.reservationRequest(seatNo);
    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.activity_seat_choice_iv_seat1:
                if (seat1 == 0) {
                    ChoiceDialog choiceDialog = new ChoiceDialog(mContext, new ChoiceDialog.CustomLIstener() {
                        @Override
                        public void yesClick() {
                            try {
                                reservationRequest(1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void noClick() {

                        }
                    });
                    choiceDialog.show();
                } else {
                    showCustomToast("이 좌석은 예약할 수 없습니다.");
                }
                break;
            case R.id.activity_seat_choice_iv_seat4:
                if (seat1 == 0) {
                    ChoiceDialog choiceDialog = new ChoiceDialog(mContext, new ChoiceDialog.CustomLIstener() {
                        @Override
                        public void yesClick() {
                            try {
                                reservationRequest(2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void noClick() {

                        }
                    });
                    choiceDialog.show();
                } else {
                    showCustomToast("이 좌석은 예약할 수 없습니다.");
                }
                break;

            default:
                break;
        }
    }


    @Override
    public void getSeatSuccess(List<Result> results) {
        hideProgressDialog();

        // seatA : seat no1, seatB : seat no2
        Result seatA = results.get(0);
        Result seatB = results.get(1);

        // 0: 사람 없음 1: 사람 있음 2: 임산부가 앉아 있음 3: 예약됨
        switch (seatA.getStatus()) {
            case 0:
                mIvSeat1.setImageDrawable(getDrawable(R.drawable.ic_status_0));
                break;
            case 1:
                mIvSeat1.setImageDrawable(getDrawable(R.drawable.ic_status_1));
                break;
            case 2:
                mIvSeat1.setImageDrawable(getDrawable(R.drawable.ic_status_2));
                break;
            case 3:
                mIvSeat1.setImageDrawable(getDrawable(R.drawable.ic_status_3));
                break;
        }

        switch (seatB.getStatus()) {
            case 0:
                mIvSeat2.setImageDrawable(getDrawable(R.drawable.ic_status_0));
                break;
            case 1:
                mIvSeat2.setImageDrawable(getDrawable(R.drawable.ic_status_1));
                break;
            case 2:
                mIvSeat2.setImageDrawable(getDrawable(R.drawable.ic_status_2));
                break;
            case 3:
                mIvSeat2.setImageDrawable(getDrawable(R.drawable.ic_status_3));
                break;
        }

    }

    @Override
    public void getSeatFailure(String message) {
        showCustomToast("서버 연결 실패");
    }

    @Override
    public void reservationRequsetSuccess() {
        hideProgressDialog();
        showProgressDialog();
        final ChoiceService choiceService = new ChoiceService(this);
        choiceService.tryGetSeat();
    }
}
