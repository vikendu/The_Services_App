package com.vikendu.theservicesapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.admin.AdminFeedActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private SharedPreferences loginState;
    private Intent intent;

    private final int SPLASH_DISPLAY_LENGTH = 900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        loginState = getSharedPreferences("login", MODE_PRIVATE);

        new Handler().postDelayed(() -> redirection(), SPLASH_DISPLAY_LENGTH);
    }

    private void redirection() {
        if(loginState.getBoolean("logged",false)) {
            if (loginState.getBoolean("isReceiver", false)) {
                intent = new Intent(this, com.vikendu.theservicesapp.kotlin.activities.BuyersHomeActivity.class);
            } else if (loginState.getBoolean("isProvider", false)) {
                intent = new Intent(this, ProvidersHomeActivity.class);
            } else if (loginState.getBoolean("isAdmin", false)) {
                intent = new Intent(this, AdminFeedActivity.class);
            } else {
                //TODO: Some error
            }
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        finish();
        startActivity(intent);
    }
}