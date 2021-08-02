package com.vikendu.theservicesapp.kotlin.activities.ui.buyers.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikendu.theservicesapp.kotlin.repos.FeedRepo
import com.vikendu.theservicesapp.models.Advert

class SearchViewModel : ViewModel() {
    private val feedRepo = FeedRepo()
    private var mutableLiveData: MutableLiveData<List<Advert>>? = null

    fun getAllAds(): LiveData<List<Advert>>? {
        if (mutableLiveData == null) {
            mutableLiveData = feedRepo.requestFeedAdverts()
        }
        return mutableLiveData
    }

    fun search(query: String, arrayList: ArrayList<Advert>): LiveData<List<Advert>> {
        val resultList = ArrayList<Advert>()
        val mutableSearchData = MutableLiveData<List<Advert>>()
        for(it in arrayList) {
            if (it.tagLine.equals(query, true) ||
                it.tagLine.contains(query, true)) {
                resultList.add(it)
            }
        }
        mutableSearchData.value = resultList
        return mutableSearchData
    }

    fun removeListener() {
        feedRepo.removeListener()
    }
}