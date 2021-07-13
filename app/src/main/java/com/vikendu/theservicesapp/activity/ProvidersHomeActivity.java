package com.vikendu.theservicesapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vikendu.theservicesapp.R;

public class ProvidersHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers_home);
    }

    public void goToAdCreationTool(View view) {
        Intent intent = new Intent(this, AdCreationActivity.class);
        startActivity(intent);
    }

    public void goToPendingAdsActivity(View view) {
        // TODO: go to the same activity as that of the FEED just fill it up with req data
        Intent intent = new Intent(this, ProviderHomeHelper.class);
        intent.putExtra("isApproved", false);
        startActivity(intent);
    }

    public void goToApprovedAdsActivity(View view) {
        // TODO: go to the same activity as that of the FEED just fill it up with req data
        Intent intent = new Intent(this, ProviderHomeHelper.class);
        intent.putExtra("isApproved", true);
        startActivity(intent);
    }

    public void goToFeedActivity(View view) {
        Intent intent = new Intent(this, FeedActivity.class);
//        intent.putExtra("latitude", "something");
//        intent.putExtra("longitude", "something");
        startActivity(intent);
    }

    public void goToChatActivity(View view) {
        Intent intent = new Intent(this, ChatContactActivity.class);
        intent.putExtra("listenOn", "providers");
        startActivity(intent);
    }
}