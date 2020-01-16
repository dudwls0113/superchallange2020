package com.softsquared.android.superchallange2020.src.signUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.BaseActivity;
import com.softsquared.android.superchallange2020.src.verify.VerifyActivity;


public class SignUpActivity extends BaseActivity {

    private EditText mEdtId;
    private EditText mEdtName;
    private EditText mEdtPassword;
    private EditText mEdtPasswordVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEdtId = findViewById(R.id.edt_sign_up_id);
        mEdtName = findViewById(R.id.edt_sign_up_name);
        mEdtPassword = findViewById(R.id.edt_sign_up_password);
        mEdtPasswordVerify = findViewById(R.id.edt_sign_up_password_verify);

    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_up_btn:
                if(!mEdtPassword.getText().toString().equals(mEdtPasswordVerify.getText().toString())){
                    showCustomToast("비밀번호가 다릅니다.");
                    break;
                }
                Intent intent = new Intent(this, VerifyActivity.class);
                intent.putExtra("id", mEdtId.getText().toString());
                intent.putExtra("name", mEdtName.getText().toString());
                intent.putExtra("password", mEdtPassword.getText().toString());
                startActivity(intent);

                break;
            default:
                break;
        }
    }
}
