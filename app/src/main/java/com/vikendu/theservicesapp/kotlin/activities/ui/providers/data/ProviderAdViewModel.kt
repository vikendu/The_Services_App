package com.vikendu.theservicesapp.kotlin.activities.ui.providers.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikendu.theservicesapp.kotlin.repos.ProviderAdReo
import com.vikendu.theservicesapp.models.Advert

class ProviderAdViewModel : ViewModel() {
    private val providerAds = ProviderAdReo()
    private var mutableLiveData: MutableLiveData<List<Advert>>? = null

    fun getAllAds(): LiveData<List<Advert>>? {
        if (mutableLiveData == null) {
            mutableLiveData = providerAds.requestProviderAdverts()
        }
        return mutableLiveData
    }

    fun getApprovedAds(arrayList: ArrayList<Advert>): ArrayList<Advert> {
        var resultantArrayList = ArrayList<Advert>()
        arrayList.forEach { i -> if (i.isApproved) { resultantArrayList.add(i) } }
        return resultantArrayList
    }

    fun getUnapprovedAds(arrayList: ArrayList<Advert>): ArrayList<Advert> {
        var resultantArrayList = ArrayList<Advert>()
        arrayList.forEach { i -> if (!i.isApproved) { resultantArrayList.add(i) } }
        return resultantArrayList
    }
}