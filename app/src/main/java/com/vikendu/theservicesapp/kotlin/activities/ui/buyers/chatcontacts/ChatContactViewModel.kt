package com.vikendu.theservicesapp.kotlin.activities.ui.buyers.chatcontacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikendu.theservicesapp.kotlin.repos.ChatContactRepo

class ChatContactViewModel: ViewModel() {
    private val chatContactRepo: ChatContactRepo =
        ChatContactRepo()
    private var mutableLiveData: MutableLiveData<HashMap<String, String>>? = null

    fun getChatContacts(): LiveData<HashMap<String, String>>? {
        if(mutableLiveData == null) {
            mutableLiveData = chatContactRepo.requestChatData()
        }
        return mutableLiveData
    }
}