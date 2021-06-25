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
    private Advert mAdvert;

    private FirebaseDatabase mDatabase;

    ServiceProvider serviceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        adCardRv = findViewById(R.id.idRVAdCreation);
        advertArrayList = new ArrayList<>();

        mDatabase = getFirebaseDatabase();
        getProviderAdData();

        serviceProvider = new ServiceProvider(0, "Vikendu", "Singh", "NaN", "vikendu@gmail.com", advertArrayList);
    }

    private void getProviderAdData() {
        DatabaseReference mDatabaseAdvertRef = mDatabase.getReference("adverts");

        ValueEventListener advertListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                advertArrayList.clear();
                for(DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                    mAdvert = providerSnapshot.getValue(Advert.class);
                    advertArrayList.add(mAdvert);
                }
                updateFeed(advertArrayList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("On DataChange Listener", "onCancelled", databaseError.toException());
            }
        };
        mDatabaseAdvertRef.child("approved").addValueEventListener(advertListener);
    }

    private void updateFeed(ArrayList advertList) {
        AdCardAdapter adCardAdapter = new AdCardAdapter(this, advertList, serviceProvider);
        Log.d("Array ->>", ""+advertList.size());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adCardRv.setLayoutManager(linearLayoutManager);
        adCardRv.setAdapter(adCardAdapter);

    }
}