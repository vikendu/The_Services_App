package com.vikendu.theservicesapp.repos;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.models.Advert;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.vikendu.theservicesapp.utils.ResourceUtil.getFirebaseDatabase;

/**
 * This repository returns all the
 * approved ads only.
 */
public class FeedRepo {

    private DatabaseReference databaseReference;
    private ValueEventListener advertListener;
    private ArrayList<Advert> advertArrayList;

    public MutableLiveData<List<Advert>> requestFeedAdverts() {

        final MutableLiveData<List<Advert>> mutableLiveData = new MutableLiveData<>();

        advertArrayList = new ArrayList<>();
        databaseReference = getFirebaseDatabase().getReference("adverts");

        advertListener = databaseReference.child("approved").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                advertArrayList.clear();
                for (DataSnapshot providerSnapshot : snapshot.getChildren()) {
                    advertArrayList.add(providerSnapshot.getValue(Advert.class));
                }
                mutableLiveData.setValue(advertArrayList);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return mutableLiveData;
    }

    public void removeListener() {
        databaseReference.removeEventListener(advertListener);
    }
}
