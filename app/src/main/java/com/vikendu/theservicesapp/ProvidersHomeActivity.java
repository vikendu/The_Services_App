package com.vikendu.theservicesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProvidersHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers_home);
    }

    public void goToAdCreationTool(View view) {
        Intent intent = new Intent(this, AdCreationActivity.class);
        finish();
        startActivity(intent);
    }
}