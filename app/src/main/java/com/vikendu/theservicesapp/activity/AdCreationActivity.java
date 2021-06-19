package com.vikendu.theservicesapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.model.Advert;
import com.vikendu.theservicesapp.model.ServiceProvider;
import com.vikendu.theservicesapp.util.ActivityUtil;
import com.vikendu.theservicesapp.util.FirebaseUtil;
import com.vikendu.theservicesapp.util.ResourceUtil;

import java.util.Objects;

import static com.vikendu.theservicesapp.util.ActivityUtil.createNoActionSnackbar;
import static com.vikendu.theservicesapp.util.ActivityUtil.hideKeyBoard;
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
    private ServiceProvider serviceProvider;

    private int adCount;
    private String rating;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_creation_tool);

        mDatabaseProviderRef = getFirebaseDatabase().getReference("providers");

        mTagLine = findViewById(R.id.idAdCreationTaglineInput);
        mAdDescription = findViewById(R.id.idAdCreationDescInput);
        mPaisa = findViewById(R.id.idAdCreationPriceInput);

        adTagLinePreview = findViewById(R.id.idAdTagLine);
        adDescriptionPreview = findViewById(R.id.idAdDesc);
        adPricePreview = findViewById(R.id.idAdPrice);
        adStarRatingPreview = findViewById(R.id.idProviderRating);

        uid = FirebaseUtil.getUid();
        getServiceProvider();
    }

    private void getServiceProvider() {
            ValueEventListener serviceProviderListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviceProvider = dataSnapshot.getValue(ServiceProvider.class);
                    createPreview();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mDatabaseProviderRef.child(Objects.requireNonNull(uid)).addValueEventListener(serviceProviderListener);
    }

    private boolean areAnyFieldsEmpty() {
        return ResourceUtil.getString(mTagLine).equals("") || ResourceUtil.getString(mAdDescription).equals("") || ResourceUtil.getString(mPaisa).equals("");
    }

    private void createPreview() {
        ActivityUtil.hideKeyBoard(this, mPaisa);

        rating = serviceProvider.getRating();
        adCount = serviceProvider.getAdCount();

        if ( !ResourceUtil.getString(mTagLine).equals("") ) {
            adTagLinePreview.setText(ResourceUtil.getString(mTagLine));
        }
        if ( !ResourceUtil.getString(mAdDescription).equals("") ) {
            adDescriptionPreview.setText(ResourceUtil.getString(mAdDescription));
        }
        if ( !ResourceUtil.getString(mPaisa).equals("") ) {
            adPricePreview.setText("₹"+ResourceUtil.getString(mPaisa));
        }
        if ( !rating.equals("") ) {
            adStarRatingPreview.setText(rating);
        }
    }

    public void showAdPreview(View view) {
        if ( !areAnyFieldsEmpty() ) {
            hideKeyBoard(this, mPaisa);
            createPreview();
        } else {
            createNoActionSnackbar(view, "Please fill out all the fields").show();
        }
    }

    public void submitForApproval(View view) {
        if ( !areAnyFieldsEmpty() ) {
            createPreview();
            ActivityUtil.hideKeyBoard(this, mPaisa);

            Advert ad = new Advert("default",
                    "default",
                    ResourceUtil.getString(mTagLine),
                    ResourceUtil.getString(mAdDescription),
                    ResourceUtil.getString(mPaisa),
                    false,
                    false);

            String adIndex = Integer.toString(adCount + 1);

            // TODO: Change constant 6 with value from subscription plan
            if(adCount < 6) {
                mDatabaseProviderRef.child(uid).child("advert").child(adIndex).setValue(ad);

                mDatabaseProviderRef.child(uid).child("adCount").setValue(adCount + 1)
                        .addOnSuccessListener(e -> Log.d("insert", "providerRef inserted"))
                        .addOnFailureListener(e -> Log.d("insert", "failed"));
            } else {
                // TODO: Sell them a premium plan
            }
        } else {
            createNoActionSnackbar(view, "Please fill out all the fields").show();
        }
        // TODO: Once this function is done -> Get the F out of this activity.
    }
}