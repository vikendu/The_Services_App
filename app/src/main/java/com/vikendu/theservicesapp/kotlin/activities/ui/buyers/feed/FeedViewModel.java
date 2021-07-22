package com.vikendu.theservicesapp.kotlin.activities.ui.buyers.feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vikendu.theservicesapp.kotlin.repos.FeedRepo;
import com.vikendu.theservicesapp.models.Advert;

import java.util.List;

public class FeedViewModel extends ViewModel {
    private FeedRepo feedRepo;
    private MutableLiveData<List<Advert>> mutableLiveData;

    public FeedViewModel() {
        feedRepo = new FeedRepo();
    }

    public LiveData<List<Advert>> getFeedAds() {
        if(mutableLiveData == null) {
            mutableLiveData = feedRepo.requestFeedAdverts();
        }
        return mutableLiveData;
    }

    public void removeListener() {
        feedRepo.removeListener();
    }

}
