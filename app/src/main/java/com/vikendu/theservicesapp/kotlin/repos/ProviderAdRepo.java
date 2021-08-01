package com.vikendu.theservicesapp.kotlin.repos;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.models.Advert;
import com.vikendu.theservicesapp.utils.FirebaseUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.vikendu.theservicesapp.utils.ResourceUtil.getFirebaseDatabase;

public class ProviderAdRepo {
    private final DatabaseReference databaseReference;
    private ValueEventListener advertListener;
    private final ArrayList<Advert> advertArrayList;
    private final MutableLiveData<List<Advert>> mutableLiveData;

    private final String uId;

    public ProviderAdRepo() {
        advertArrayList = new ArrayList<>();
        databaseReference = getFirebaseDatabase().getReference("providers");
        mutableLiveData = new MutableLiveData<>();
        uId = FirebaseUtil.getUid();
    }

    public MutableLiveData<List<Advert>> requestProviderAdverts() {
        advertListener = databaseReference
                .child(uId)
                .child("ads")
                .addValueEventListener(new ValueEventListener() {
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
