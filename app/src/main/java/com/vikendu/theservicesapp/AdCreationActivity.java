package com.vikendu.theservicesapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdCreationActivity extends AppCompatActivity {

    private AutoCompleteTextView mTagLine;
    private AutoCompleteTextView mAdDescription;
    private AutoCompleteTextView mPaisa;

    private TextView adTagLinePreview;
    private TextView adDescriptionPreview;
    private TextView adPricePreview;
    private TextView adStarRatingPreview;

    private DatabaseReference mDatabaseProviderRef;
    private DatabaseReference mDatabaseAdvertRef;
    private FirebaseUser mFirebaseuser;
    //TODO: Add a variable of ServiceProvider class & store the value returned by the callback.

    private String rating;
    private String adCount;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_creation_tool);

        mFirebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseProviderRef = FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("providers");
        mDatabaseAdvertRef = FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("adverts");

        mTagLine = findViewById(R.id.idAdCreationTaglineInput);
        mAdDescription = findViewById(R.id.idAdCreationDescInput);
        mPaisa = findViewById(R.id.idAdCreationPriceInput);

        adTagLinePreview = findViewById(R.id.idAdTagLine);
        adDescriptionPreview = findViewById(R.id.idAdDesc);
        adPricePreview = findViewById(R.id.idAdPrice);
        adStarRatingPreview = findViewById(R.id.idProviderRating);

        if(mFirebaseuser != null) {
            uid = mFirebaseuser.getUid();
        } else {
            //TODO: tell the user that something went wrong & Log them out
        }
        //TODO: 2 Separate functions aren't required. The object returned should be used for both values.
        getStarRating(value -> rating = value);
        getAdCount(value -> adCount = value);
    }

    private void checkForEmptyFields() {
        // TODO: Check if any fields have been left empty or not.
    }

    private void getStarRating(ServiceProviderCallback callback) {
        //Get what the rating of the guy is
        //TODO: This needs to be a ValueListener; currently it is updating only with onCreate() once
        mDatabaseProviderRef.child(uid).child("rating").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("firebase", "Error getting data", task.getException());
            } else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                callback.onCallBack(String.valueOf(task.getResult().getValue()));
            }
        });
    }

    private void getAdCount(ServiceProviderCallback callback) {
        //Get how many ads the current guy has already uploaded
        //TODO: This needs to be a ValueListener; currently it is updating only with onCreate() once
        mDatabaseProviderRef.child(uid).child("adCount").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("firebase", "Error getting data", task.getException());
            } else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                callback.onCallBack(String.valueOf(task.getResult().getValue()));
            }
        });
    }

    public void showAdPreview(View view) {
        checkForEmptyFields();
        hideKeyBoard(this, mPaisa);

        adTagLinePreview.setText(getString(mTagLine));
        adDescriptionPreview.setText(getString(mAdDescription));
        adPricePreview.setText(getString(mPaisa));
        adStarRatingPreview.setText(rating);
    }

    private void hideKeyBoard(Context context, TextView textView) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }

    private String getString(AutoCompleteTextView acTextView) {
        return acTextView.getText().toString();
    }

    public void submitForApproval(View view) {
        checkForEmptyFields();
        hideKeyBoard(this, mPaisa);

        int adCountInt = Integer.parseInt(adCount.trim()); // TODO: serviceProvider.getAdCount()
        Advert ad = new Advert("default", "default", getString(mTagLine),
                getString(mAdDescription), getString(mPaisa), false, false);

        if(adCountInt < 5) {
            mDatabaseAdvertRef.child(uid).child("ad"+adCountInt+1).setValue(ad);
            mDatabaseProviderRef.child(uid).child("adCount").setValue(adCountInt+1);
        } else {
            // TODO: Sell them a premium plan
        }
        // TODO: Once this function is done -> Get the F out of this activity.
    }
}