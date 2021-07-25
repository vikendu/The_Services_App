package com.vikendu.theservicesapp.kotlin.activities.ui.providers.localfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikendu.theservicesapp.kotlin.repos.FeedRepo
import com.vikendu.theservicesapp.models.Advert

class LocalFeedViewModel : ViewModel() {
    private val feedRepo = FeedRepo()
    private var mutableLiveData: MutableLiveData<List<Advert>>? = null

    fun getAds(): LiveData<List<Advert>>? {
        if (mutableLiveData == null) {
            mutableLiveData = feedRepo.requestFeedAdverts()
        }
        return mutableLiveData
    }

    fun removeListener() {
        feedRepo.removeListener()
    }
}