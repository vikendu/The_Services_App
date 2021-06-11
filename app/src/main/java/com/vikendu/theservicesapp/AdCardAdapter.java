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

import java.util.ArrayList;

public class AdCardAdapter extends RecyclerView.Adapter<AdCardAdapter.Viewholder> {

    private final Context context;
    private final ArrayList<Advert> advertArrayList;
    private final ServiceProvider serviceProvider;

    public AdCardAdapter(Context context, ArrayList<Advert> advertArrayList, ServiceProvider serviceProvider) {
        this.context = context;
        this.advertArrayList = advertArrayList;
        this.serviceProvider = serviceProvider;
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
        // TODO: Fix the rating system
        holder.providersRating.setText(serviceProvider.getRating());
    }

    @Override
    public int getItemCount() {
        return advertArrayList.size();
    }
    public static class Viewholder extends RecyclerView.ViewHolder {
        private final TextView adTagLine;
        private final TextView adDesc;
        private final TextView adPrice;
        private final TextView providersRating;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            adTagLine = itemView.findViewById(R.id.idAdTagLine);
            adDesc = itemView.findViewById((R.id.idAdDesc));
            adPrice = itemView.findViewById((R.id.idAdPrice));
            providersRating = itemView.findViewById(R.id.idProviderRating);
        }
    }
}
