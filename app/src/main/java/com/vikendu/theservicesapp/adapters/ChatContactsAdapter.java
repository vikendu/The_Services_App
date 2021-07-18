package com.vikendu.theservicesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vikendu.theservicesapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatContactsAdapter extends RecyclerView.Adapter<ChatContactsAdapter.Viewholder> {

    private final Context context;
    private final ArrayList<String> keySet;
    private final ArrayList<String> nameList;

    public ChatContactsAdapter(Context context, ArrayList<String> keys, ArrayList<String> names) {
        this.context = context;
        this.keySet = keys;
        this.nameList = names;
    }

    @NonNull
    @NotNull
    @Override
    public ChatContactsAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contact_id, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatContactsAdapter.Viewholder holder, int position) {
        String chatId = keySet.get(position);
        String name = nameList.get(position);

        holder.chatId.setText(chatId);
        holder.contactName.setText(name);
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView contactName;
        private final TextView chatId;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            chatId = itemView.findViewById((R.id.idChatId));
            contactName = itemView.findViewById(R.id.idContactName);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}
