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

public class SeatChoiceActivity extends BaseActivity {

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

}
