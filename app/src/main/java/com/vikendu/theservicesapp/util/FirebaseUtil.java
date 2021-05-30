package com.vikendu.theservicesapp.util;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.Advert;
import com.vikendu.theservicesapp.ServiceProvider;

import java.util.Objects;

public class FirebaseUtil {

    static FirebaseUser mFirebaseuser = FirebaseAuth.getInstance().getCurrentUser();

    public static String getUid(){
        if(mFirebaseuser != null) {
            return mFirebaseuser.getUid();
        } else {
            //TODO: tell the user that something went wrong & Log them out
            return null;
        }
    }

    public static void getServiceProvider(ServiceProviderCallback callback) {
        DatabaseReference mDatabaseProviderRef = FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("providers");

        ValueEventListener serviceProviderListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onCallBack(dataSnapshot.getValue(ServiceProvider.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("On DataChange Listener", "onCancelled", databaseError.toException());
            }
        };
        mDatabaseProviderRef.child(Objects.requireNonNull(getUid())).addValueEventListener(serviceProviderListener);
    }

    public static void insertServiceProvider(DatabaseReference databaseReference, ServiceProvider serviceProvider) {
        databaseReference.setValue(serviceProvider)
                .addOnSuccessListener(e -> Log.d("insertSP", "Successful"))
                .addOnFailureListener(e -> Log.d("InsertSP", "Failure"));
    }

    public static void insertServiceProvider(String userId, DatabaseReference databaseReference, ServiceProvider serviceProvider) {
        databaseReference.child(userId).setValue(serviceProvider)
                .addOnSuccessListener(e -> Log.d("insertSP", "Successful"))
                .addOnFailureListener(e -> Log.d("InsertSP", "Failure"));
    }

    public static void insertAdvertData(String userId, DatabaseReference databaseReference, Advert advert, String adNum) {
        databaseReference.child(userId).child(adNum).setValue(advert)
                .addOnSuccessListener(e -> Log.d("insertAd", "Successful"))
                .addOnFailureListener(e -> Log.d("InsertAd", "Failure"));
    }
}
