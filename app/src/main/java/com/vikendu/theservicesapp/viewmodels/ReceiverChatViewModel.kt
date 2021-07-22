package com.vikendu.theservicesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikendu.theservicesapp.repos.ReceiverChatRepo

class ReceiverChatViewModel: ViewModel() {
    private val chatRepo: ReceiverChatRepo = ReceiverChatRepo()
    private var mutableLiveData: MutableLiveData<HashMap<String, String>>? = null

    fun getChatContacts(): LiveData<HashMap<String, String>>? {
        if(mutableLiveData == null) {
            mutableLiveData = chatRepo.requestChatData()
        }
        return mutableLiveData
    }
}