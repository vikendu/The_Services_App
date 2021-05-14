package com.vikendu.theservicesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CreateAdActivity extends AppCompatActivity {

    private RecyclerView adCardRv;
    private ArrayList<Advert> advertArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

        adCardRv = findViewById(R.id.idRVAdCreation);

        advertArrayList = new ArrayList<Advert>();

        advertArrayList.add(new Advert("Category", "Free Diagnosis!", "I will mend electrical stuff", "900", 3.5f));
        AdCardAdapter adCardAdapter = new AdCardAdapter(this, advertArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adCardRv.setLayoutManager(linearLayoutManager);
        adCardRv.setAdapter(adCardAdapter);
    }
}