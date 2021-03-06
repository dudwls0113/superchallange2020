package com.softsquared.android.superchallange2020.src.curation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.BaseActivity;
import com.softsquared.android.superchallange2020.src.curation.interfaces.CurationActivityView;
import com.softsquared.android.superchallange2020.src.main.SeatCheckDialog;
import com.softsquared.android.superchallange2020.src.seat_choice.SeatChoiceActivity;
import com.softsquared.android.superchallange2020.src.station.StationActivity;

import org.json.JSONObject;

import static com.softsquared.android.superchallange2020.src.ApplicationClass.sSharedPreferences;

public class CurationActivity extends BaseActivity implements CurationActivityView {
    LinearLayout mTextViewJuan;
    String fcmToken;
    Context mContext;
    boolean mIsVerify = false;
    TextView mTextViewHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curation);
        mContext = this;

        mTextViewJuan = findViewById(R.id.activity_curation_tv_juan);
        mTextViewHide = findViewById(R.id.hide);
        mTextViewHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HeartCheckDialog heartCheckDialog = new HeartCheckDialog(mContext, new HeartCheckDialog.CustomLIstener() {
                    @Override
                    public void yesClick() {

                    }

                    @Override
                    public void noClick() {

                    }
                });
                heartCheckDialog.show();
            }
        });
        mTextViewJuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIsVerify){
                    Intent intent = new Intent(getApplicationContext(), StationActivity.class);
                    startActivity(intent);
                }
                else{
                    showCustomToast("미인증 유저는 이용하실 수 없숩니다.");
                }

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
                        int seatNum = getIntent().getIntExtra("seatNo", 0);

                        JSONObject params = new JSONObject();
                        try {
                            params.put("seatNo", seatNum);
                        } catch (Exception e) {
                            //   Log.d(TAG, "error: " + e);
                            return;
                        }
                        showProgressDialog();
                        final CurationService curationService = new CurationService((CurationActivityView)mContext ,params);
                        curationService.postReservation();
                    }

                    @Override
                    public void noClick() {
                        showProgressDialog();

                        int seatNum = getIntent().getIntExtra("seatNo", 0);

                        JSONObject params = new JSONObject();
                        try {
                            params.put("seatNo", seatNum);
                        } catch (Exception e) {
                            //   Log.d(TAG, "error: " + e);
                            return;
                        }

                        final CurationService curationService = new CurationService((CurationActivityView)mContext ,params);
                        curationService.postSoundRequest();
                    }
                });
                seatChoiceActivity.show();
            } else {

            }
        }

    }

    @Override
    public void reservationSuccess(String text) {
        hideProgressDialog();
//        showCustomToast(text);
    }

    @Override
    public void reservationFailure(String message) {
        hideProgressDialog();
//        showCustomToast(message);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        String id = getIntent().getStringExtra("id");
        String id = sSharedPreferences.getString("id", null);
        JSONObject params = new JSONObject();
        try {
            params.put("id", id);
        } catch (Exception e) {
            return;
        }

        final CurationService curationService = new CurationService((CurationActivityView)mContext ,params);
        curationService.getVerify();

        Log.d("로그", "아이디 "+id);

    }

    @Override
    public void getVerifySuccess(boolean isVerify) {
        Log.d("로그", "isVerify "+isVerify);
        mIsVerify = isVerify;

    }
}
