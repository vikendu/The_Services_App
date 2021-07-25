package com.vikendu.theservicesapp.kotlin.activities.ui.buyers.chatcontacts

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
import com.vikendu.theservicesapp.databinding.FragmentBuyersChatContactBinding
import com.vikendu.theservicesapp.utils.RecyclerItemClickListener
import java.util.ArrayList

class ReceiversChatContactFragment : Fragment(R.layout.fragment_buyers_chat_contact) {
    private var _binding: FragmentBuyersChatContactBinding? = null
    private val binding get() = _binding!!

    private var keySet: ArrayList<String> = ArrayList()
    private var names: ArrayList<String> = ArrayList()
    private val chatViewModel = ReceiversChatContactViewModel()
    private var contactMap: HashMap<String, String> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyersChatContactBinding.inflate(inflater, container, false)
        val rootView = binding.root

        chatViewModel.getChatContacts()?.observe(viewLifecycleOwner, {
            contactMap = it
            updateFeed(it)
        })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.idRVContactList.addOnItemTouchListener(RecyclerItemClickListener(
            context,
            binding.idRVContactList,
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
        binding.idRVContactList.layoutManager = layoutManager
        binding.idRVContactList.adapter = chatContactsAdapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}