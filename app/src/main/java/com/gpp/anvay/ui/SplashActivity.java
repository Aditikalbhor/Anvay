package com.gpp.anvay.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.gpp.anvay.R;
import com.gpp.anvay.data.PreferenceManager;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION_MS = 1400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) return;

                PreferenceManager preferenceManager = new PreferenceManager(SplashActivity.this);
                Intent nextIntent;
                if (preferenceManager.isLoggedIn()) {
                    nextIntent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    nextIntent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(nextIntent);
                finish();
            }
        }, SPLASH_DURATION_MS);
    }
}
