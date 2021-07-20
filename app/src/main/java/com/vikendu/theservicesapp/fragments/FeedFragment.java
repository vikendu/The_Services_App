package com.vikendu.theservicesapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vikendu.theservicesapp.R;
import com.vikendu.theservicesapp.activities.ProviderDetailsActivity;
import com.vikendu.theservicesapp.adapters.AdCardAdapter;
import com.vikendu.theservicesapp.models.Advert;
import com.vikendu.theservicesapp.utils.RecyclerItemClickListener;
import com.vikendu.theservicesapp.viewmodels.FeedViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is not used
 * Same functionality provided by - FeedFragment.kt
 */
public class FeedFragment extends Fragment {

    private FeedViewModel feedViewModel;
    private ArrayList<Advert> advertArrayList;
    private RecyclerView adRecyclerView;

    public FeedFragment(){
        super(R.layout.fragment_feed);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        feedViewModel = new FeedViewModel();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        adRecyclerView = getView().findViewById(R.id.idRVAdCreation);

        feedViewModel.getFeedAds().observe(getViewLifecycleOwner(), adverts -> {
            advertArrayList = (ArrayList<Advert>) adverts;
            updateFeed(advertArrayList);
        });

        adRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getContext(),
                adRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(view.getContext(), ProviderDetailsActivity.class);
                        intent.putExtra("advert", advertArrayList.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }
        ));
        super.onViewCreated(view, savedInstanceState);
    }

    private void updateFeed(ArrayList<Advert> adList) {
        AdCardAdapter adCardAdapter = new AdCardAdapter(getContext(), adList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adRecyclerView.setLayoutManager(manager);
        adRecyclerView.setAdapter(adCardAdapter);
    }

    @Override
    public void onDestroy() {
        feedViewModel.removeListener();
        super.onDestroy();
    }
}
