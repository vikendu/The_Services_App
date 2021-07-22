package com.vikendu.theservicesapp.kotlin.activities.ui.providers.adcreation;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vikendu.theservicesapp.R;

public class AdCreationFragment extends Fragment {

    private AdCreationViewModel mViewModel;

    public static AdCreationFragment newInstance() {
        return new AdCreationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_ad, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdCreationViewModel.class);
        // TODO: Use the ViewModel
    }

}