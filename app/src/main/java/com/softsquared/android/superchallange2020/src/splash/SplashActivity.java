package com.softsquared.android.superchallange2020.src.splash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.BaseActivity;
import com.softsquared.android.superchallange2020.src.curation.CurationActivity;
import com.softsquared.android.superchallange2020.src.main.MainActivity;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread splashThread;

        splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        // Wait given period of time or exit on touch
                        wait(1000);
                    }
                } catch (InterruptedException ignored) {
                }
                finish();
                Intent intent = new Intent(getApplicationContext(), CurationActivity.class);
                startActivity(intent);
            }
        };
        splashThread.start();

    }

}
