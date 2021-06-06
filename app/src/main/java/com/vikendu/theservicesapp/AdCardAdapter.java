package com.vikendu.theservicesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vikendu.theservicesapp.model.Advert;
import com.vikendu.theservicesapp.model.ServiceProvider;
import com.vikendu.theservicesapp.util.FirebaseUtil;

import java.util.ArrayList;

public class AdCardAdapter extends RecyclerView.Adapter<AdCardAdapter.Viewholder> {

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
        AdCardAdapter.Viewholder viewHolder = new AdCardAdapter.Viewholder(view);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Advert model = advertArrayList.get(position);

        holder.adTagLine.setText(model.getTagLine());
        holder.adDesc.setText(model.getAdDescription());
        holder.adPrice.setText(model.getAdPrice());
        // TODO: Fix the rating system
        holder.providersRating.setText("xoxo");
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

            //TODO: add UI updates to onDataChange itself
            FirebaseUtil.getServiceProvider(value -> serviceProvider = value);
        }
    }
}
