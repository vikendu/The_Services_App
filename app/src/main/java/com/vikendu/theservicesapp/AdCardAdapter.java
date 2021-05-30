package com.vikendu.theservicesapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikendu.theservicesapp.util.FirebaseUtil;
import com.vikendu.theservicesapp.util.ServiceProviderCallback;

import java.util.ArrayList;

public class AdCardAdapter extends RecyclerView.Adapter<AdCardAdapter.Viewholder> {

    // TODO: This whole file needs to be rewritten according to the new Advert.java
    private Context context;
    private ArrayList<Advert> advertArrayList;

    private ServiceProvider serviceProvider;

    public AdCardAdapter(Context context, ArrayList<Advert> advertArrayList){
        this.context = context;
        this.advertArrayList = advertArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ad_card_format, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Advert model = advertArrayList.get(position);

        holder.adTagLine.setText(model.getTagLine());
        holder.adDesc.setText(model.getAdDescription());
        holder.adPrice.setText(model.getAdPrice());
        holder.providersRating.setText(serviceProvider.getRating());
    }

    @Override
    public int getItemCount() {
        return advertArrayList.size();
    }
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView adTagLine, adDesc, adPrice, providersRating;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            adTagLine = itemView.findViewById(R.id.idAdTagLine);
            adDesc = itemView.findViewById((R.id.idAdDesc));
            adPrice = itemView.findViewById((R.id.idAdPrice));
            providersRating = itemView.findViewById(R.id.idProviderRating);

            FirebaseUtil.getServiceProvider(value -> serviceProvider = value);
        }
    }
}
