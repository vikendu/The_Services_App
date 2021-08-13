package com.vikendu.theservicesapp.kotlin.activities.ui.buyers.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vikendu.theservicesapp.kotlin.repos.FeedRepo
import com.vikendu.theservicesapp.models.Advert

class FeedViewModel {
    private val feedRepo = FeedRepo()
    private var mutableLiveData: MutableLiveData<List<Advert>>? = null

    fun getFeedAds(): LiveData<List<Advert>>? {
        if (mutableLiveData == null) {
            mutableLiveData = feedRepo.requestFeedAdverts()
        }
        return mutableLiveData
    }

    fun removeListener() {
        feedRepo.removeListener()
    }
}