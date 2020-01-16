package com.softsquared.android.superchallange2020.src.curation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.station.StationActivity;

public class CurationActivity extends AppCompatActivity {
    TextView mTextViewJuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curation);

        mTextViewJuan = findViewById(R.id.activity_curation_tv_juan);

        mTextViewJuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StationActivity.class);
                startActivity(intent);
            }
        });
    }
}
