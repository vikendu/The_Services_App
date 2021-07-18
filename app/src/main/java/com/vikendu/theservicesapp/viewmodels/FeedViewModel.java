package com.vikendu.theservicesapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vikendu.theservicesapp.models.Advert;
import com.vikendu.theservicesapp.repos.FeedRepository;

import java.util.List;

public class FeedViewModel extends ViewModel {
    private FeedRepository feedRepository;
    private MutableLiveData<List<Advert>> mutableLiveData;

    public FeedViewModel() {
        feedRepository = new FeedRepository();
    }

    public LiveData<List<Advert>> getFeedAds() {
        if(mutableLiveData == null) {
            mutableLiveData = feedRepository.requestFeedAdverts();
        }
        return mutableLiveData;
    }
}
