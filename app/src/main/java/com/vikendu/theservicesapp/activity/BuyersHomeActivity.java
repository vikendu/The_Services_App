package com.vikendu.theservicesapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vikendu.theservicesapp.R;

public class BuyersHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyers_home);
    }

    public void goToChatActivity(View view) {
        Intent intent = new Intent(this, ChatContactActivity.class);
        intent.putExtra("listenOn", "receivers");
        finish();
        startActivity(intent);
    }

    public void goToFeed(View view) {
        Intent intent = new Intent(this, FeedActivity.class);
        finish();
        startActivity(intent);
    }
}