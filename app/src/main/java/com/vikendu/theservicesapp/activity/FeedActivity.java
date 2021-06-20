package com.vikendu.theservicesapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.AdCardAdapter;
import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.model.Advert;
import com.vikendu.theservicesapp.model.ServiceProvider;

import java.util.ArrayList;

import static com.vikendu.theservicesapp.util.ResourceUtil.getFirebaseDatabase;

public class FeedActivity extends AppCompatActivity {

    private RecyclerView adCardRv;
    private ArrayList<Advert> advertArrayList;
    private ServiceProvider mServiceProvider;

    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        adCardRv = findViewById(R.id.idRVAdCreation);
        advertArrayList = new ArrayList<>();

        mDatabase = getFirebaseDatabase();
        getProviderAdData();
    }

    private void getProviderAdData() {
        DatabaseReference mDatabaseProviderRef = mDatabase.getReference("providers");

        ValueEventListener serviceProviderListener = new ValueEventListener() {
            ArrayList<Advert> ad = new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                advertArrayList.clear();
                for(DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                    mServiceProvider = providerSnapshot.getValue(ServiceProvider.class);
                    advertArrayList = mServiceProvider.getAdvertArrayList();
                    Log.d("ArraySize->>advert ->>", ""+advertArrayList.size());
                    for(Advert advertLineItem : advertArrayList) {
                        if(advertLineItem.isApproved()) {
                            ad.add(advertLineItem);
                        }
                    }
                }
                updateFeed(ad);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("On DataChange Listener", "onCancelled", databaseError.toException());
            }
        };
        mDatabaseProviderRef.addValueEventListener(serviceProviderListener);
    }

    private void updateFeed(ArrayList advertList) {
        AdCardAdapter adCardAdapter = new AdCardAdapter(this, advertList, mServiceProvider);
        Log.d("Array ->>", ""+advertList.size());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adCardRv.setLayoutManager(linearLayoutManager);
        adCardRv.setAdapter(adCardAdapter);

    }
}