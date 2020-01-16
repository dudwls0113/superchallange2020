package com.softsquared.android.superchallange2020.src.seat_choice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.BaseActivity;
import com.softsquared.android.superchallange2020.src.seat_choice.interfaces.ChoiceActivityView;
import com.softsquared.android.superchallange2020.src.seat_choice.model.Result;

import java.util.List;

public class SeatChoiceActivity extends BaseActivity implements ChoiceActivityView {

    Spinner spinner;
    Context mContext;
    String[] strings = {"1-1", "1-2", "1-3", "1-4"};


    int seat1, seat2, seat3, seat4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_choice);
        mContext = this;

        spinner = findViewById(R.id.activity_seat_choice_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.custom_spinner, strings);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        final ChoiceService choiceService = new ChoiceService(this);
        choiceService.tryGetSeat();

    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.activity_seat_choice_iv_seat1:
                if(seat1 == 0){
                    ChoiceDialog choiceDialog = new ChoiceDialog(mContext, new ChoiceDialog.CustomLIstener() {
                        @Override
                        public void yesClick() {

                        }

                        @Override
                        public void noClick() {

                        }
                    });
                    choiceDialog.show();
                }
                else {
                    showCustomToast("이 좌석은 예약할 수 없습니다.");
                }
                break;
            case R.id.activity_seat_choice_iv_seat2:

                break;
            case R.id.activity_seat_choice_iv_seat3:

                break;
            case R.id.activity_seat_choice_iv_seat4:

                break;

            default:
                break;
        }
    }


    @Override
    public void getSeatSuccess(List<Result> results) {

        // seatA : seat no1, seatB : seat no2
        Result seatA = results.get(0);
        Result seatB = results.get(1);

        // 0: 사람 없음 1: 사람 있음 2: 임산부가 앉아 있음 3: 예약됨
        switch (seatA.getStatus()){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

        switch (seatB.getStatus()){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

    }

    @Override
    public void getSeatFailure(String message) {
        showCustomToast("서버 연결 실패");
    }
}
