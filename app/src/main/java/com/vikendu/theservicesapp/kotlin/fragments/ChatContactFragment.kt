package com.vikendu.theservicesapp.kotlin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.activities.ChatActivity
import com.vikendu.theservicesapp.adapters.ChatContactsAdapter
import com.vikendu.theservicesapp.utils.RecyclerItemClickListener
import com.vikendu.theservicesapp.viewmodels.ReceiverChatViewModel
import kotlinx.android.synthetic.main.fragment_chat_contact.*
import java.util.ArrayList

class ChatContactFragment : Fragment(R.layout.fragment_chat_contact) {
    private var keySet: ArrayList<String> = ArrayList()
    private var names: ArrayList<String> = ArrayList()
    private val chatViewModel = ReceiverChatViewModel()
    private var contactMap: HashMap<String, String> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatViewModel.getChatContacts()?.observe(viewLifecycleOwner, {
            contactMap = it
            updateFeed(it)
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        idRVContactList.addOnItemTouchListener(RecyclerItemClickListener(
            context,
            idRVContactList,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val intent = Intent(view?.context, ChatActivity::class.java)
                    intent.putExtra("chatId", keySet[position])
                    startActivity(intent)
                }

                override fun onLongItemClick(view: View?, position: Int) {
                    TODO("Not yet implemented")
                }

            }
        ))
        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateFeed(map: HashMap<String, String>?) {
        keySet = ArrayList(map?.keys)
        names = ArrayList(map?.values)

        val chatContactsAdapter = ChatContactsAdapter(context, keySet, names)
        val layoutManager = LinearLayoutManager(context)
        idRVContactList.layoutManager = layoutManager
        idRVContactList.adapter = chatContactsAdapter
    }
}