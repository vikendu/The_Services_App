package com.vikendu.theservicesapp.kotlin.repos;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.models.ServiceProvider;
import com.vikendu.theservicesapp.utils.FirebaseUtil;

import org.jetbrains.annotations.NotNull;

import static com.vikendu.theservicesapp.utils.ResourceUtil.getFirebaseDatabase;

public class ServiceProviderRepo {
    public final MutableLiveData<ServiceProvider> mutableLiveData;
    private final FirebaseDatabase database;
    private final String uId;
    private final DatabaseReference databaseReference;

    private ValueEventListener serviceProviderListener;
    private ServiceProvider serviceProvider;

    public ServiceProviderRepo() {
        mutableLiveData = new MutableLiveData<>();
        database = getFirebaseDatabase();
        databaseReference = database.getReference();
        uId = FirebaseUtil.getUid();
    }

    public MutableLiveData<ServiceProvider> getServiceProvider() {
        serviceProviderListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                serviceProvider = dataSnapshot.getValue(ServiceProvider.class);
                if(serviceProvider != null) {
                   mutableLiveData.setValue(serviceProvider);
                }
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        };
        databaseReference
                .child("providers")
                .child(uId)
                .addValueEventListener(serviceProviderListener);

        return mutableLiveData;
    }

    public void removeListener() {
        databaseReference.removeEventListener(serviceProviderListener);
    }
}
