package com.vikendu.theservicesapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    private DatabaseReference mDatabase;
    private FirebaseUser mFirebaseuser;

    private double rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_creation_tool);

        mFirebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("providers");

        mTagLine = findViewById(R.id.idAdCreationTaglineInput);
        mAdDescription = findViewById(R.id.idAdCreationDescInput);
        mPaisa = findViewById(R.id.idAdCreationPriceInput);

        adTagLinePreview = findViewById(R.id.idAdTagLine);
        adDescriptionPreview = findViewById(R.id.idAdDesc);
        adPricePreview = findViewById(R.id.idAdPrice);
        adStarRatingPreview = findViewById(R.id.idProviderRating);
    }

    private double getStarRating(FirebaseUser user){
        String uid;
        if (user != null) {
            uid = user.getUid();

            mDatabase.child(uid).child("rating").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    rating = (double) task.getResult().getValue();
                }
            });
        } else {
            //TODO: Create an alert dialog telling the MF to log in
        }
        return rating;
    }

    public void showAdPreview(View view) {
        adTagLinePreview.setText(getString(mTagLine));
        adDescriptionPreview.setText(getString(mAdDescription));
        adPricePreview.setText(getString(mPaisa));
        adStarRatingPreview.setText(Double.toString(getStarRating(mFirebaseuser)));
    }

    private String getString(AutoCompleteTextView acTextView){
        return acTextView.getText().toString();
    }

    public void submitForApproval(View view) {
    }
}