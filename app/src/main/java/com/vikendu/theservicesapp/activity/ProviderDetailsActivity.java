package com.vikendu.theservicesapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.model.Advert;
import com.vikendu.theservicesapp.model.ChatContact;
import com.vikendu.theservicesapp.model.ServiceProvider;
import com.vikendu.theservicesapp.model.ServiceReceiver;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.vikendu.theservicesapp.util.FirebaseUtil.getUid;
import static com.vikendu.theservicesapp.util.ResourceUtil.getFirebaseDatabase;

public class ProviderDetailsActivity extends AppCompatActivity {

    private Advert ad;

    private TextView tagLine;
    private TextView adDescription;
    private TextView paisa;
    private TextView starRating;

    private TextView providerName;
    private TextView providerPhone;
    private TextView providerEmail;
    private TextView providerAboutMe;

    private String providerContactName;
    private String receiverContactName;
    private String receiverUid;
    private String chatId;

    private ChatContact chatContact;

    private ArrayList<String> providerContactList;
    private ArrayList<String> receiverContactList;

    private DatabaseReference databaseProviderRef;
    private DatabaseReference databaseReceiverRef;
    private DatabaseReference databaseChatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);

        databaseProviderRef = getFirebaseDatabase().getReference("providers");
        databaseReceiverRef = getFirebaseDatabase().getReference("receivers");
        databaseChatRef = getFirebaseDatabase().getReference("chats");

        Intent intent = getIntent();
        ad = (Advert)intent.getSerializableExtra("advert");

        tagLine = findViewById(R.id.idAdTagLine);
        adDescription = findViewById(R.id.idAdDesc);
        paisa = findViewById(R.id.idAdPrice);
        starRating = findViewById(R.id.idProviderRating);

        providerName = findViewById(R.id.idProviderName);
        providerPhone = findViewById(R.id.idProviderPhone);
        providerEmail = findViewById(R.id.idProviderEmail);
        providerAboutMe = findViewById(R.id.idProviderAboutMe);

        providerContactList = new ArrayList<>();
        receiverContactList = new ArrayList<>();

        receiverUid = getUid();

        setAdCardDetails();
        setProviderDetails(ad.getUid());
    }

    private void setAdCardDetails() {
        tagLine.setText(ad.getTagLine());
        adDescription.setText(ad.getAdDescription());
        paisa.setText(ad.getAdPrice());
        //Not implemented yet.
        //starRating.setText(ad.getCachedRating());
    }

    private void setProviderDetails(String uid) {
        databaseProviderRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ServiceProvider provider = snapshot.getValue(ServiceProvider.class);

                if(provider != null) {
                    providerName.setText(provider.getFirstName() + " " + provider.getLastName());
                    //providerPhone.setText();
                    providerEmail.setText(provider.getEmail());
                    //providerAboutMe.setText();
                    starRating.setText(provider.getRating());

                    providerContactName = provider.getFirstName();

                    if(provider.getContactList() != null) {
                        providerContactList = provider.getContactList();
                    }
                    getReceiverDetail();
                } else {
                    Log.d("ProviderDetailsHome", "EPIC FAIL");
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void getReceiverDetail() {
        databaseReceiverRef.child(receiverUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ServiceReceiver receiver = snapshot.getValue(ServiceReceiver.class);

                if(receiver != null) {
                    receiverContactName = receiver.getFirstName();

                    if(receiver.getContactList() != null) {
                        receiverContactList = receiver.getContactList();
                    }
                    createChatContactNode();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void startChat(View view) {
        databaseReceiverRef.child(receiverUid).child("contactList").setValue(receiverContactList);
        databaseProviderRef.child(ad.getUid()).child("contactList").setValue(providerContactList);
        databaseChatRef.child(chatId).setValue(chatContact);

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("chatId", chatId);
        finish();
        startActivity(intent);
    }

    private void createChatContactNode() {
        chatContact = new ChatContact(providerContactName, receiverContactName);
        chatId = createUniqueChatId(ad.getUid(), receiverUid);

        providerContactList.add(chatId);
        receiverContactList.add(chatId);
    }

    private String createUniqueChatId(String providerUid, String receiverUid) {
        return providerUid.substring(0, 4) + providerUid.substring(providerUid.length() - 4, providerUid.length() - 1)
                + receiverUid.substring(0, 4) + receiverUid.substring(receiverUid.length() - 4, receiverUid.length() - 1);
    }
}