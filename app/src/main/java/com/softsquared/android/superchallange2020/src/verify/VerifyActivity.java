package com.softsquared.android.superchallange2020.src.verify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.BaseActivity;
import com.softsquared.android.superchallange2020.src.curation.CurationActivity;


public class VerifyActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);


    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_up_btn:

                Intent intent = new Intent(this, CurationActivity.class);
//                intent.putExtra("id", mEdtId.getText().toString());
//                intent.putExtra("name", mEdtName.getText().toString());
//                intent.putExtra("password", mEdtPassword.getText().toString());
//
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
