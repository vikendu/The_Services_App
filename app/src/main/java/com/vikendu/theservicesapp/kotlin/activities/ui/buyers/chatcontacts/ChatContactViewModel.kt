package com.vikendu.theservicesapp.kotlin.activities.ui.buyers.chatcontacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikendu.theservicesapp.kotlin.repos.ReceiverChatContactRepo

class ChatContactViewModel: ViewModel() {
    private val receiverChatContactRepo: ReceiverChatContactRepo =
        ReceiverChatContactRepo()
    private var mutableLiveData: MutableLiveData<HashMap<String, String>>? = null

    fun getChatContacts(): LiveData<HashMap<String, String>>? {
        if(mutableLiveData == null) {
            mutableLiveData = receiverChatContactRepo.requestChatData()
        }
        return mutableLiveData
    }
}