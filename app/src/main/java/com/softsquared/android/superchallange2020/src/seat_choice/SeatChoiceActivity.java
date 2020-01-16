package com.softsquared.android.superchallange2020.src.seat_choice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.BaseActivity;

public class SeatChoiceActivity extends BaseActivity {

    Spinner spinner;
    Context mContext;
    String[] strings = {"1-1", "1-2", "1-3", "1-4"};

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


}
