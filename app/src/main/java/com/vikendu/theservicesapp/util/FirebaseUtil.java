package com.vikendu.theservicesapp.util;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.vikendu.theservicesapp.model.Advert;
import com.vikendu.theservicesapp.model.ServiceProvider;
import com.vikendu.theservicesapp.model.ServiceReceiver;

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
     *
     * @param userId
     * @param databaseReference
     * @param serviceReceiver
     */
    public static void insertServiceReceiver(String userId, DatabaseReference databaseReference, ServiceReceiver serviceReceiver) {
        databaseReference.child(userId).setValue(serviceReceiver)
                .addOnSuccessListener(e -> Log.d("insertReceiver", "Successful"))
                .addOnFailureListener(e -> Log.d("InsertReceiver", "Failure"));
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
