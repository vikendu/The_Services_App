package com.vikendu.theservicesapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.model.Message;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {

    private Activity activity;
    private DatabaseReference reference;
    private String displayName;
    private ArrayList<DataSnapshot> dataSnapshotList;

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            dataSnapshotList.add(snapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {

        }
    };

    public ChatAdapter(Activity activity, DatabaseReference databaseReference, String displayName) {
        this.activity = activity;
        this.reference = databaseReference.child("message");
        this.displayName = displayName;

        reference.addChildEventListener(childEventListener);
        dataSnapshotList = new ArrayList<>();
    }

    static class ViewHolder {
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return dataSnapshotList.size();
    }

    @Override
    public Message getItem(int position) {
        DataSnapshot snapshot = dataSnapshotList.get(position);
        return snapshot.getValue(Message.class);
    }

    @Override
    public long getItemId(int i) {
        // TODO: Implement later
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean isMe = false;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_chat_msg_row, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.authorName = convertView.findViewById(R.id.author);
            holder.body = convertView.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();

            convertView.setTag(holder);
        }
        final Message message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        if(message.getAuthor().equals(displayName)) {
            isMe = true;
        }
        setChatRowStyle(isMe, holder);
        String author = message.getAuthor();
        holder.authorName.setText(author);
        String msg = message.getMessage();
        holder.body.setText(msg);

        return convertView;
    }

    private void setChatRowStyle(boolean isMe, ViewHolder holder) {
        if (isMe) {
            holder.params.gravity = Gravity.END;
            holder.authorName.setTextColor(Color.parseColor("#a1dd70"));
            holder.body.setBackgroundResource(R.drawable.bubble2);
        } else {
            holder.params.gravity = Gravity.START;
            holder.authorName.setTextColor(Color.BLUE);
            holder.body.setBackgroundResource(R.drawable.bubble1);
        }
        holder.authorName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);
    }

    public void cleanup() {
        reference.removeEventListener(childEventListener);
    }
}
