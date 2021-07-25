package com.vikendu.theservicesapp.kotlin.repos;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import static com.vikendu.theservicesapp.utils.ResourceUtil.getFirebaseDatabase;

public class AdvertIdRepo {
    public final MutableLiveData<String> mutableLiveData;
    private final FirebaseDatabase database;
    private final DatabaseReference databaseReference;

    private ValueEventListener adIdListener;

    public AdvertIdRepo() {
        mutableLiveData = new MutableLiveData<>();
        database = getFirebaseDatabase();
        databaseReference = database.getReference();
    }

    public MutableLiveData<String> getCurrentAdId() {
        adIdListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String id = snapshot.getValue(String.class);
                if(id != null) {
                    mutableLiveData.setValue(id);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference
                .child("adverts")
                .child("adId")
                .addValueEventListener(adIdListener);

        return mutableLiveData;
    }

    public void removeListener() {
        databaseReference.removeEventListener(adIdListener);
    }
}
