package com.vikendu.theservicesapp;

import android.app.AlertDialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private ServiceProvider serviceProvider;
    private AlertDialog.Builder alertDialogBuilder;

    private int adCount;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_creation_tool);

        FirebaseUser mFirebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseAdvertRef = FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("adverts");
        mDatabaseProviderRef = FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("providers");

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
        getServiceProvider(value -> serviceProvider = value);

        alertDialogBuilder = new AlertDialog.Builder(AdCreationActivity.this)
                .setTitle("Problem")
                .setMessage("Make sure you are connected to the internet and try again.")
                .setNegativeButton(android.R.string.no, null);
    }

    private void checkForEmptyFields() {
        // TODO: Check if any fields have been left empty or not.
    }

    private void getServiceProvider(ServiceProviderCallback callback) {
        ValueEventListener serviceProviderListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onCallBack(dataSnapshot.getValue(ServiceProvider.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("On DataChange Listener", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabaseProviderRef.child(uid).addValueEventListener(serviceProviderListener);
    }

    public void showAdPreview(View view) {
        checkForEmptyFields();
        hideKeyBoard(this, mPaisa);

        String rating = serviceProvider.getRating();
        adCount = serviceProvider.getAdCount();

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

        Advert ad = new Advert("default", "default", getString(mTagLine),
                getString(mAdDescription), getString(mPaisa), false, false);

        if(adCount < 6) {
            mDatabaseAdvertRef.child(uid).child("ad"+adCount).setValue(ad)
                    .addOnSuccessListener(e -> Log.d("insert", "advertRef inserted"))
                    .addOnFailureListener(e -> alertDialogBuilder.show());

            mDatabaseProviderRef.child(uid).child("adCount").setValue(adCount + 1)
                    .addOnSuccessListener(e -> Log.d("insert", "providerRef inserted"))
                    .addOnFailureListener(e -> alertDialogBuilder.show());
        } else {
            // TODO: Sell them a premium plan
        }
        // TODO: Once this function is done -> Get the F out of this activity.
    }
}