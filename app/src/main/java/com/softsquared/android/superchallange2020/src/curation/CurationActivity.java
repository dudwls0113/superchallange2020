package com.softsquared.android.superchallange2020.src.curation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.main.SeatCheckDialog;
import com.softsquared.android.superchallange2020.src.seat_choice.SeatChoiceActivity;
import com.softsquared.android.superchallange2020.src.station.StationActivity;

public class CurationActivity extends AppCompatActivity {
    TextView mTextViewJuan;
    String fcmToken;

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

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,
                new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        fcmToken = instanceIdResult.getToken();
                        Log.d("Firebase", "token: " + fcmToken);
//                        patchFcmToken();
                    }
                });

        if (getIntent() != null) {
            if (getIntent().getIntExtra("seatNo", 0) != 0) {
                SeatCheckDialog seatChoiceActivity = new SeatCheckDialog(this, new SeatCheckDialog.CustomLIstener() {
                    @Override
                    public void yesClick() {

                    }

                    @Override
                    public void noClick() {

                    }
                });
                seatChoiceActivity.show();
            } else {

            }
        }

    }
}
