package com.vikendu.theservicesapp;

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
import com.vikendu.theservicesapp.model.Advert;
import com.vikendu.theservicesapp.model.ServiceProvider;
import com.vikendu.theservicesapp.util.AdvertCallback;
import com.vikendu.theservicesapp.util.FirebaseUtil;
import com.vikendu.theservicesapp.util.ServiceProviderCallback;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.vikendu.theservicesapp.util.FirebaseUtil.getUid;

public class CreateAdActivity extends AppCompatActivity {

    private RecyclerView adCardRv;
    private ArrayList<Advert> advertArrayList;
    private ArrayList<Advert> advertArrayListCallback;
    private ServiceProvider mServiceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

        adCardRv = findViewById(R.id.idRVAdCreation);

        advertArrayList = new ArrayList<>();
        advertArrayListCallback = new ArrayList<>();

        // TODO: Get an Intent from where you are coming and Inflate the card view as per that
        getDataSnapshot();
    }

    private void getDataSnapshot() {
        DatabaseReference mDatabaseAdvertRef = FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("adverts");
        ValueEventListener advertListener = new ValueEventListener() {
            Advert ad;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                advertArrayList.clear();
                for(DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    ad = adSnapshot.getValue(Advert.class);
                    advertArrayList.add(ad);
                }
                getServiceProvider();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("On DataChange Listener", "onCancelled", databaseError.toException());
            }
        };
        mDatabaseAdvertRef.child(Objects.requireNonNull(getUid())).addValueEventListener(advertListener);
    }
    public void getServiceProvider() {
        DatabaseReference mDatabaseProviderRef = FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("providers");

        ValueEventListener serviceProviderListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
        AdCardAdapter adCardAdapter = new AdCardAdapter(CreateAdActivity.this, advertArrayList, mServiceProvider);
        Log.d("Array ->>", Integer.toString(advertArrayList.size()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreateAdActivity.this, LinearLayoutManager.VERTICAL, false);
        adCardRv.setLayoutManager(linearLayoutManager);
        adCardRv.setAdapter(adCardAdapter);
    }
}