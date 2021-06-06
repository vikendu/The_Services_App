package com.vikendu.theservicesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vikendu.theservicesapp.util.FirebaseUtil;

public class ProvidersHomeActivity extends AppCompatActivity {

    private String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers_home);

        uId = FirebaseUtil.getUid();
    }

    public void goToAdCreationTool(View view) {
        Intent intent = new Intent(this, AdCreationActivity.class);
        finish();
        startActivity(intent);
    }

    public void goToPendingAdsActivity(View view) {
        // TODO: go to the same activity as that of the FEED just fill it up with req data
    }

    public void goToApprovedAdsActivity(View view) {
        // TODO: go to the same activity as that of the FEED just fill it up with req data
    }
}