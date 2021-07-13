package com.vikendu.theservicesapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.adapter.ChatContactsAdapter;
import com.vikendu.theservicesapp.model.ChatContact;
import com.vikendu.theservicesapp.util.FirebaseUtil;
import com.vikendu.theservicesapp.util.RecyclerItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import static com.vikendu.theservicesapp.util.ResourceUtil.getFirebaseDatabase;

public class ChatContactActivity extends AppCompatActivity {

    private String listenOn;
    private String uid;
    private String chatId;
    private HashMap<String, String> nameMap;
    private ArrayList<String> keySet;
    private String resolvedName;

    private RecyclerView contactView;

    private FirebaseDatabase database;
    private DatabaseReference currentUserReference;
    private DatabaseReference chatReference;
    private ValueEventListener nameResolverListener;
    private ValueEventListener contactListListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_contact);

        contactView = findViewById(R.id.idRVContactList);
        database = getFirebaseDatabase();
        nameMap = new HashMap<>();

        //TODO: Get info on whether to listen to "provider" or "receiver" from incoming intent
        Bundle bundle = getIntent().getExtras();
        listenOn = bundle.getString("listenOn");

        uid = FirebaseUtil.getUid();
        getContactList();

        //Listener for individual items
        contactView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, contactView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //get chat Id
                        String key = keySet.get(position);

                        Intent intent = new Intent(view.getContext(), ChatActivity.class);
                        intent.putExtra("chatId", key);
                        startActivity(intent);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        //TODO: Delete options here
                    }
                })
        );
    }

    private void getContactList() {
        if(listenOn.equals("providers")) {
            currentUserReference = database.getReference("providers");
        } else if (listenOn.equals("receivers")) {
            currentUserReference = database.getReference("receivers");
        }

        contactListListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                nameMap.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chatId = dataSnapshot.getValue(String.class);
                    resolveName(chatId, listenOn);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w("On DataChange Listener", "onCancelled", error.toException());
            }
        };
        currentUserReference.child(uid).child("contactList").addValueEventListener(contactListListener);
    }

    private void resolveName(String chatId, String typeOfUser) {
        chatReference = database.getReference("chats");

        nameResolverListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ChatContact contact = snapshot.getValue(ChatContact.class);
                if(typeOfUser.equals("providers")) {
                    resolvedName = contact.getReceiverName();
                } else if (typeOfUser.equals("receivers")) {
                    resolvedName = contact.getProviderName();
                }
                nameMap.put(chatId, resolvedName);
                updateContactList(nameMap);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        };
        chatReference.child(chatId).addValueEventListener(nameResolverListener);
    }

    private void updateContactList(HashMap<String, String> map) {
        keySet = new ArrayList<>(map.keySet());
        ArrayList<String> names = new ArrayList<>(map.values());

        ChatContactsAdapter contactAdapter = new ChatContactsAdapter(this, keySet, names);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contactView.setLayoutManager(linearLayoutManager);
        contactView.setAdapter(contactAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        chatReference.removeEventListener(nameResolverListener);
        currentUserReference.removeEventListener(contactListListener);
    }
}