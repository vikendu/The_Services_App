package com.vikendu.theservicesapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.model.Advert;
import com.vikendu.theservicesapp.model.ServiceProvider;
import com.vikendu.theservicesapp.util.ActivityUtil;
import com.vikendu.theservicesapp.util.FirebaseUtil;
import com.vikendu.theservicesapp.util.ResourceUtil;

import static com.vikendu.theservicesapp.util.ResourceUtil.getFirebaseDatabase;

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

        mDatabaseAdvertRef = getFirebaseDatabase().getReference("adverts");
        mDatabaseProviderRef = getFirebaseDatabase().getReference("providers");

        mTagLine = findViewById(R.id.idAdCreationTaglineInput);
        mAdDescription = findViewById(R.id.idAdCreationDescInput);
        mPaisa = findViewById(R.id.idAdCreationPriceInput);

        adTagLinePreview = findViewById(R.id.idAdTagLine);
        adDescriptionPreview = findViewById(R.id.idAdDesc);
        adPricePreview = findViewById(R.id.idAdPrice);
        adStarRatingPreview = findViewById(R.id.idProviderRating);

        uid = FirebaseUtil.getUid();
        FirebaseUtil.getServiceProviderOnCallback(value -> serviceProvider = value);

        // TODO: add an alert dialog builder here for CRUD failures
    }

    private void checkForEmptyFields() {
        // TODO: Check if any fields have been left empty or not.
    }

    public void showAdPreview(View view) {
        checkForEmptyFields();
        ActivityUtil.hideKeyBoard(this, mPaisa);

        //TODO: get stuff needs to be in onCreate() Lmao
        String rating = serviceProvider.getRating();
        adCount = serviceProvider.getAdCount();

        adTagLinePreview.setText(ResourceUtil.getString(mTagLine));
        adDescriptionPreview.setText(ResourceUtil.getString(mAdDescription));
        adPricePreview.setText(ResourceUtil.getString(mPaisa));
        adStarRatingPreview.setText(rating);
    }

    public void submitForApproval(View view) {
        checkForEmptyFields();
        ActivityUtil.hideKeyBoard(this, mPaisa);

        Advert ad = new Advert("default",
                "default",
                ResourceUtil.getString(mTagLine),
                ResourceUtil.getString(mAdDescription),
                ResourceUtil.getString(mPaisa),
                false,
                false);

        if(adCount < 6) {
            // FirebaseUtil.insertAdvertData(uid, mDatabaseAdvertRef, ad, "ad"+adCount);
            mDatabaseProviderRef.child(uid).child("advert").push().setValue(ad);

            mDatabaseProviderRef.child(uid).child("adCount").setValue(adCount + 1)
                    .addOnSuccessListener(e -> Log.d("insert", "providerRef inserted"))
                    .addOnFailureListener(e -> Log.d("insert", "failed"));
        } else {
            // TODO: Sell them a premium plan
        }
        // TODO: Once this function is done -> Get the F out of this activity.
    }
}