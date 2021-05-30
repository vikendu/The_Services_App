package com.vikendu.theservicesapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vikendu.theservicesapp.util.FirebaseUtil;
import com.vikendu.theservicesapp.util.ResourceUtil;

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

    private int adCount;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_creation_tool);

        mDatabaseAdvertRef = FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("adverts");
        mDatabaseProviderRef = FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("providers");

        mTagLine = findViewById(R.id.idAdCreationTaglineInput);
        mAdDescription = findViewById(R.id.idAdCreationDescInput);
        mPaisa = findViewById(R.id.idAdCreationPriceInput);

        adTagLinePreview = findViewById(R.id.idAdTagLine);
        adDescriptionPreview = findViewById(R.id.idAdDesc);
        adPricePreview = findViewById(R.id.idAdPrice);
        adStarRatingPreview = findViewById(R.id.idProviderRating);

        uid = FirebaseUtil.getUid();
        FirebaseUtil.getServiceProvider(value -> serviceProvider = value);

        // TODO: add an alert dialog builder here for CRUD failures
    }

    private void checkForEmptyFields() {
        // TODO: Check if any fields have been left empty or not.
    }

    public void showAdPreview(View view) {
        checkForEmptyFields();
        hideKeyBoard(this, mPaisa);

        String rating = serviceProvider.getRating();
        adCount = serviceProvider.getAdCount();

        adTagLinePreview.setText(ResourceUtil.getString(mTagLine));
        adDescriptionPreview.setText(ResourceUtil.getString(mAdDescription));
        adPricePreview.setText(ResourceUtil.getString(mPaisa));
        adStarRatingPreview.setText(rating);
    }

    private void hideKeyBoard(Context context, TextView textView) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }

    public void submitForApproval(View view) {
        checkForEmptyFields();
        hideKeyBoard(this, mPaisa);

        Advert ad = new Advert("default",
                "default",
                ResourceUtil.getString(mTagLine),
                ResourceUtil.getString(mAdDescription),
                ResourceUtil.getString(mPaisa),
                false,
                false);

        if(adCount < 6) {
            FirebaseUtil.insertAdvertData(uid, mDatabaseAdvertRef, ad, "ad"+adCount);

            mDatabaseProviderRef.child(uid).child("adCount").setValue(adCount + 1)
                    .addOnSuccessListener(e -> Log.d("insert", "providerRef inserted"))
                    .addOnFailureListener(e -> Log.d("insert", "failed"));
        } else {
            // TODO: Sell them a premium plan
        }
        // TODO: Once this function is done -> Get the F out of this activity.
    }
}