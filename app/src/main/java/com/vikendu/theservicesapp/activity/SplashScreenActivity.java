package com.vikendu.theservicesapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.vikendu.theservicesapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    private SharedPreferences loginState;
    private Intent intent;

    private final int SPLASH_DISPLAY_LENGTH = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        loginState = getSharedPreferences("login", MODE_PRIVATE);

        new Handler().postDelayed(() -> redirection(), SPLASH_DISPLAY_LENGTH);
    }

    private void redirection() {
        if(loginState.getBoolean("logged",false)) {
            if(loginState.getBoolean("isReceiver", false)) {
                intent = new Intent(this, ProvidersHomeActivity.class);
            } else {
                intent = new Intent(this, BuyersHomeActivity.class);
            }
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        finish();
        startActivity(intent);
    }
}