package com.vikendu.theservicesapp.repos;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.models.ChatContact;
import com.vikendu.theservicesapp.utils.FirebaseUtil;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.vikendu.theservicesapp.utils.ResourceUtil.getFirebaseDatabase;

public class ReceiverChatRepo {

    private String chatId;
    private HashMap<String, String> nameMap;
    private String resolvedName;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public final MutableLiveData<HashMap<String, String>> mutableLiveData;

    public ReceiverChatRepo() {
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<HashMap<String, String>> requestChatData() {
        nameMap = new HashMap<>();
        database = getFirebaseDatabase();
        databaseReference = database.getReference();
        String uId = FirebaseUtil.getUid();

        ValueEventListener contactListListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                nameMap.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chatId = dataSnapshot.getValue(String.class);
                    resolveName(chatId);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        assert uId != null;
        databaseReference
                .child("receivers")
                .child(uId).child("contactList")
                .addValueEventListener(contactListListener);

        return mutableLiveData;
    }

    private void resolveName(String chatId) {
        databaseReference = database.getReference("chats");

        ValueEventListener nameResolverListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ChatContact contact = snapshot.getValue(ChatContact.class);
                if (contact != null) {
                    resolvedName = contact.getProviderName();
                }
                nameMap.put(chatId, resolvedName);
                mutableLiveData.setValue(nameMap);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        };
        databaseReference.child(chatId).addValueEventListener(nameResolverListener);
    }
}
