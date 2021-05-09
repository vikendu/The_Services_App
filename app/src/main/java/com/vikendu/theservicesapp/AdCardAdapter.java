package com.vikendu.theservicesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdCardAdapter extends RecyclerView.Adapter<AdCardAdapter.Viewholder> {

    private Context context;
    private ArrayList<Advert> advertArrayList;

    public AdCardAdapter(Context context, ArrayList<Advert> advertArrayList){
        this.context = context;
        this.advertArrayList = advertArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_card_format, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Advert model = advertArrayList.get(position);

        holder.adTagLine.setText(model.getTagLine());
        holder.adDesc.setText(model.getAdDescription());
        holder.adPrice.setText(model.getAdPrice());
    }

    @Override
    public int getItemCount() {
        return advertArrayList.size();
    }
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView adTagLine, adDesc, adPrice;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            adTagLine = itemView.findViewById(R.id.idAdTagLine);
            adDesc = itemView.findViewById((R.id.idAdDesc));
            adPrice = itemView.findViewById((R.id.idAdPrice));
        }
    }
}
