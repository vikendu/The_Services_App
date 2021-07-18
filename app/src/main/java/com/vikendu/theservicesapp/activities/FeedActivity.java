package com.vikendu.theservicesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.adapters.AdCardAdapter;
import com.vikendu.theservicesapp.models.Advert;
import com.vikendu.theservicesapp.models.ServiceProvider;
import com.vikendu.theservicesapp.utils.RecyclerItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.vikendu.theservicesapp.utils.ResourceUtil.getFirebaseDatabase;

public class FeedActivity extends AppCompatActivity {

    private RecyclerView adCardRv;
    private ArrayList<Advert> advertArrayList;
    private Advert mAdvert;
    private ServiceProvider serviceProvider;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseAdvertRef;
    private ValueEventListener advertListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        adCardRv = findViewById(R.id.idRVAdCreation);
        advertArrayList = new ArrayList<>();

        mDatabase = getFirebaseDatabase();
        getProviderAdData();

        serviceProvider = new ServiceProvider(0, "Vikendu", "Singh", "NaN", "vikendu@gmail.com", advertArrayList, null);

        adCardRv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, adCardRv, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Advert advert = advertArrayList.get(position);

                        Intent intent = new Intent(view.getContext(), ProviderDetailsActivity.class);
                        intent.putExtra("advert", advert);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    private void getProviderAdData() {
        mDatabaseAdvertRef = mDatabase.getReference("adverts");

        advertListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                advertArrayList.clear();
                for(DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                    mAdvert = providerSnapshot.getValue(Advert.class);
                    advertArrayList.add(mAdvert);
                }
                updateFeed(advertArrayList);
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        };
        mDatabaseAdvertRef.child("approved").addValueEventListener(advertListener);
    }

    private void updateFeed(ArrayList<Advert> advertList) {
        AdCardAdapter adCardAdapter = new AdCardAdapter(this, advertList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adCardRv.setLayoutManager(linearLayoutManager);
        adCardRv.setAdapter(adCardAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mDatabaseAdvertRef.removeEventListener(advertListener);
    }
}