package com.vikendu.theservicesapp.kotlin.activities.ui.providers.providerschat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikendu.theservicesapp.kotlin.repos.ProviderChatContactRepo

class ProvidersChatContactViewModel : ViewModel() {
    private val providerChatContactRepo = ProviderChatContactRepo()
    private var mutableLiveData: MutableLiveData<HashMap<String, String>>? = null

    fun getChatContacts(): LiveData<HashMap<String, String>>? {
        if(mutableLiveData == null) {
            mutableLiveData = providerChatContactRepo.requestChatData()
        }
        return mutableLiveData
    }

}