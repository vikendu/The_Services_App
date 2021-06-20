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
import java.util.Objects;

import static com.vikendu.theservicesapp.util.FirebaseUtil.getUid;
import static com.vikendu.theservicesapp.util.ResourceUtil.getFirebaseDatabase;

public class ProviderHomeHelper extends AppCompatActivity {

    private RecyclerView adCardRv;
    private ArrayList<Advert> advertArrayList;
    private ServiceProvider mServiceProvider;

    private FirebaseDatabase mDatabase;

    private boolean getApprovedAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

        adCardRv = findViewById(R.id.idRVAdCreation);
        advertArrayList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        getApprovedAds = bundle.getBoolean("isApproved");

        mDatabase = getFirebaseDatabase();
        getProviderAdData();
    }

    private void getProviderAdData() {
        DatabaseReference mDatabaseProviderRef = mDatabase.getReference("providers");

        ValueEventListener serviceProviderListener = new ValueEventListener() {
        Advert ad;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                advertArrayList.clear();
                for(DataSnapshot adSnapshot : dataSnapshot.child("advert").getChildren()) {
                    ad = adSnapshot.getValue(Advert.class);
                    if(getApprovedAds && ad.isApproved()) {
                        advertArrayList.add(ad);
                    }
                    else if(!getApprovedAds && !ad.isApproved()) {
                        advertArrayList.add(ad);
                    }
                }
                mServiceProvider = dataSnapshot.getValue(ServiceProvider.class);
                updateView(advertArrayList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("On DataChange Listener", "onCancelled", databaseError.toException());
            }
        };
        mDatabaseProviderRef.child(Objects.requireNonNull(getUid())).addValueEventListener(serviceProviderListener);
    }

    private void updateView(ArrayList<Advert> advertArrayList) {
        AdCardAdapter adCardAdapter = new AdCardAdapter(ProviderHomeHelper.this, advertArrayList, mServiceProvider);
        Log.d("Array ->>", Integer.toString(advertArrayList.size()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProviderHomeHelper.this, LinearLayoutManager.VERTICAL, false);
        adCardRv.setLayoutManager(linearLayoutManager);
        adCardRv.setAdapter(adCardAdapter);
    }


}