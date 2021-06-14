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

    public void goToPendingAdsActivity(View view) {
        // TODO: go to the same activity as that of the FEED just fill it up with req data
        Intent intent = new Intent(this, CreateAdActivity.class);
        intent.putExtra("isApproved", false);
        finish();
        startActivity(intent);
    }

    public void goToApprovedAdsActivity(View view) {
        // TODO: go to the same activity as that of the FEED just fill it up with req data
        Intent intent = new Intent(this, CreateAdActivity.class);
        intent.putExtra("isApproved", true);
        finish();
        startActivity(intent);
    }
}