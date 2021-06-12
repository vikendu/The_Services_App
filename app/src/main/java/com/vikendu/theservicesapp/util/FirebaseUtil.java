package com.vikendu.theservicesapp.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.model.Advert;
import com.vikendu.theservicesapp.model.ServiceProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class FirebaseUtil {

    static FirebaseUser mFirebaseuser = FirebaseAuth.getInstance().getCurrentUser();

    /**
     * This method fetches the current User's
     * uid, which is unique across the project
     * @return String
     */
    public static String getUid(){
        if(mFirebaseuser != null) {
            return mFirebaseuser.getUid();
        } else {
            //TODO: Don't add error here, add a toast to that activity itself.//
            Log.d("User Object - ", "Returning null");
            return null;
        }
    }

    /**
     * This method returns the ServiceProvider
     * object for current user.
     * Use only when UI updates are not going to be
     * immediate. If immediate put it in onDataChanged()
     * of the listener itself in the onCreated(..) of the
     * concerened activity.
     * @param callback
     */
    public static void getServiceProviderOnCallback(ServiceProviderCallback callback) {
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

    /**
     * insert ServiceProvider object
     * @param databaseReference
     * @param serviceProvider
     */
    public static void insertServiceProvider(DatabaseReference databaseReference, ServiceProvider serviceProvider) {
        databaseReference.setValue(serviceProvider)
                .addOnSuccessListener(e -> Log.d("insertSP", "Successful"))
                .addOnFailureListener(e -> Log.d("InsertSP", "Failure"));
    }

    /**
     * insert ServiceProvider object
     * @param userId
     * @param databaseReference
     * @param serviceProvider
     * @return void
     */
    public static void insertServiceProvider(String userId, DatabaseReference databaseReference, ServiceProvider serviceProvider) {
        databaseReference.child(userId).setValue(serviceProvider)
                .addOnSuccessListener(e -> Log.d("insertSP", "Successful"))
                .addOnFailureListener(e -> Log.d("InsertSP", "Failure"));
    }

    /**
     * Insert Advert object
     * @param userId
     * @param databaseReference
     * @param advert
     * @param adNum
     * @return void
     */
    public static void insertAdvertData(String userId, DatabaseReference databaseReference, Advert advert, String adNum) {
        databaseReference.child(userId).child(adNum).setValue(advert)
                .addOnSuccessListener(e -> Log.d("insertAd", "Successful"))
                .addOnFailureListener(e -> Log.d("InsertAd", "Failure"));
    }
}
