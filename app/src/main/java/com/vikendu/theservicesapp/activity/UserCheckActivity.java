package com.vikendu.theservicesapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.model.ServiceProvider;
import com.vikendu.theservicesapp.model.ServiceReceiver;

import org.jetbrains.annotations.NotNull;

import static com.vikendu.theservicesapp.util.FirebaseUtil.getUid;
import static com.vikendu.theservicesapp.util.ResourceUtil.getFirebaseDatabase;

public class UserCheckActivity extends AppCompatActivity {

    String uid;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_check);

        databaseReference = getFirebaseDatabase().getReference();
        uid = getUid();
        isProvider();
    }

    private void isProvider() {
        databaseReference.child("providers").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ServiceProvider user = snapshot.getValue(ServiceProvider.class);
                if(user != null) {
                    Intent intent = new Intent(UserCheckActivity.this, ProvidersHomeActivity.class);
                    finish();
                    startActivity(intent);
                } else {
                    isReceiver();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void isReceiver() {
        databaseReference.child("receivers").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ServiceReceiver user = snapshot.getValue(ServiceReceiver.class);
                if(user != null) {
                    Intent intent = new Intent(UserCheckActivity.this, BuyersHomeActivity.class);
                    finish();
                    startActivity(intent);
                } else {
                    //TODO pop up some error; "You not registered bro."
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}