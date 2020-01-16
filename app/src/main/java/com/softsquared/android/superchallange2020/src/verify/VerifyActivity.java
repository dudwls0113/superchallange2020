package com.softsquared.android.superchallange2020.src.verify;

import android.os.Bundle;
import android.view.View;

import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.BaseActivity;


public class VerifyActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);


    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_up_btn:


                break;
            default:
                break;
        }
    }
}
