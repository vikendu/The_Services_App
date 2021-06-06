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
import com.vikendu.theservicesapp.util.AdvertCallback;
import com.vikendu.theservicesapp.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.vikendu.theservicesapp.util.FirebaseUtil.getUid;

public class CreateAdActivity extends AppCompatActivity {

    private RecyclerView adCardRv;
    private ArrayList<Advert> advertArrayList;
    private ArrayList<Advert> advertArrayListCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

        adCardRv = findViewById(R.id.idRVAdCreation);

        advertArrayList = new ArrayList<>();
        advertArrayListCallback = new ArrayList<>();

        // TODO: Get an Intent from where you are coming and Inflate the card view as per that
        advertArrayList.add(new Advert("default", "default", "default", "default", "default", false, false));
        getDataSnapshot(value -> advertArrayList = value);

        Log.d("Array ->>", Integer.toString(advertArrayList.size()));


    }
//    private void inflateCardViews() {
//        advertArrayList = getAllAdverts(advertMap);
//
//        AdCardAdapter adCardAdapter = new AdCardAdapter(this, advertArrayList);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        adCardRv.setLayoutManager(linearLayoutManager);
//        adCardRv.setAdapter(adCardAdapter);
//    }

    private void getDataSnapshot(AdvertCallback callback) {
        DatabaseReference mDatabaseAdvertRef = FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("adverts");
        ValueEventListener advertListener = new ValueEventListener() {
            Advert ad;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                advertArrayList.clear();
                for(DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    ad = adSnapshot.getValue(Advert.class);
                    advertArrayListCallback.add(ad);
                    Log.d("Array ->", advertArrayListCallback.toString());
                }
                callback.onCallBack(advertArrayListCallback);
                Log.d("Array ->>", Integer.toString(advertArrayList.size()));
                AdCardAdapter adCardAdapter = new AdCardAdapter(CreateAdActivity.this, advertArrayList);
                Log.d("Array ->>", Integer.toString(advertArrayList.size()));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreateAdActivity.this, LinearLayoutManager.VERTICAL, false);
                adCardRv.setLayoutManager(linearLayoutManager);
                adCardRv.setAdapter(adCardAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("On DataChange Listener", "onCancelled", databaseError.toException());
            }
        };
        mDatabaseAdvertRef.child(Objects.requireNonNull(getUid())).addValueEventListener(advertListener);

    }
//    public void getAllAdverts(Map<String, Advert> ads) {
//        ArrayList<Advert> advertList = new ArrayList<>();
//
//        for(Map.Entry<String, Advert> entry : ads.entrySet()) {
//            Map singleAd = (Map) entry.getValue();
//            advertList.add((Advert) singleAd);
//        }
//
//    }

}