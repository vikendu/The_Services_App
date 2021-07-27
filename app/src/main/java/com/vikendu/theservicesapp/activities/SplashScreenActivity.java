package com.vikendu.theservicesapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.vikendu.theservicesapp.admin.AdminFeedActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private SharedPreferences loginState;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginState = getSharedPreferences("login", MODE_PRIVATE);
        redirection();
    }

    private void redirection() {
        if(loginState.getBoolean("logged",false)) {
            if (loginState.getBoolean("isReceiver", false)) {
                intent = new Intent(this, com.vikendu.theservicesapp.kotlin.activities.BuyersHomeActivity.class);
            } else if (loginState.getBoolean("isProvider", false)) {
                intent = new Intent(this, com.vikendu.theservicesapp.kotlin.activities.ProvidersHomeActivity.class);
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