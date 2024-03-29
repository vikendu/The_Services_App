package com.vikendu.theservicesapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.adapters.AdCardAdapter;
import com.vikendu.theservicesapp.models.Advert;
import com.vikendu.theservicesapp.models.ServiceProvider;
import com.vikendu.theservicesapp.utils.RecyclerItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.vikendu.theservicesapp.utils.ResourceUtil.getFirebaseDatabase;

public class AdminFeedActivity extends AppCompatActivity {

    private RecyclerView feedRecyclerView;
    private ArrayList<Advert> unapprovedList;
    private Advert ad;
    private Advert checkAd;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference advertReference;
    private ValueEventListener valueEventListenerAd;
    private DialogInterface.OnClickListener dialogClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feed);

        feedRecyclerView = findViewById(R.id.idRVUnApprovedList);
        unapprovedList = new ArrayList<>();

        database = getFirebaseDatabase();
        databaseReference = database.getReference();
        getPendingAds();

        feedRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, feedRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        checkAd = unapprovedList.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setMessage("Approve this Ad?").setPositiveButton("Yep", dialogClickListener)
                                .setNegativeButton("Nope", dialogClickListener);
                        builder.show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );

        dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                        checkAd.setApproved(true);
                        databaseReference
                                .child("adverts")
                                .child("approved")
                                .child(checkAd.getAdId())
                                .setValue(checkAd);

                        databaseReference
                                .child("adverts")
                                .child("notApproved")
                                .child(checkAd.getAdId())
                                .setValue(null);

                        databaseReference
                                .child("providers")
                                .child(checkAd.getUid())
                                .child("ads")
                                .child(checkAd.getAdId())
                                .setValue(checkAd);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:

                    break;
            }
        };
    }

    private void getPendingAds() {
        valueEventListenerAd =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                unapprovedList.clear();
                for(DataSnapshot adSnapshot : snapshot.getChildren()) {
                    ad = adSnapshot.getValue(Advert.class);
                    unapprovedList.add(ad);
                    updateFeed(unapprovedList);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        database
                .getReference("adverts")
                .child("notApproved")
                .addValueEventListener(valueEventListenerAd);
    }

    private void updateFeed(ArrayList<Advert> advertList) {
        AdCardAdapter adCardAdapter = new AdCardAdapter(this, advertList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        feedRecyclerView.setLayoutManager(linearLayoutManager);
        feedRecyclerView.setAdapter(adCardAdapter);
    }
}