package com.vikendu.theservicesapp.activity;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.adapter.ChatAdapter;
import com.vikendu.theservicesapp.model.Message;

import static com.vikendu.theservicesapp.util.ResourceUtil.getFirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference databaseChatReference;
    private ChatAdapter chatAdapter;
    private String chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle bundle = getIntent().getExtras();
        chatId = bundle.getString("chatId");

        usernameSetup();
        databaseChatReference = getFirebaseDatabase().getReference("chats").child(chatId);

        mInputText = findViewById(R.id.messageInput);
        mSendButton = findViewById(R.id.sendButton);
        mChatListView = findViewById(R.id.chat_list_view);

        mInputText.setOnEditorActionListener((v, actionId, event) -> { sendMessage(); return true; });
        mSendButton.setOnClickListener(v -> sendMessage());
    }

    @Override
    public void onStart() {
        super.onStart();
        chatAdapter = new ChatAdapter(this, databaseChatReference, mDisplayName);
        mChatListView.setAdapter(chatAdapter);
        scrollMyListViewToBottom();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatAdapter.cleanup();
    }

    private void scrollMyListViewToBottom() {
        int position = 0;
        mChatListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        if(chatAdapter.getCount() >= 1) {
            position -= 1;
        }
        int finalPosition = position;
        mChatListView.post(() -> mChatListView.smoothScrollToPosition(finalPosition));
    }

    private void usernameSetup() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getDisplayName() != null) {
            mDisplayName = user.getDisplayName();
        } else {
            mDisplayName = "Anon";
        }
    }

    private void sendMessage() {
        String message_text = mInputText.getText().toString();

        if (!message_text.equals("")) {
            Message message = new Message(message_text, mDisplayName);
            databaseChatReference.child("message").push().setValue(message);
            mInputText.setText("");
        }
    }
}