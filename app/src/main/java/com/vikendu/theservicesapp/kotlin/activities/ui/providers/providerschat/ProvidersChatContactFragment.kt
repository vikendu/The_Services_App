package com.vikendu.theservicesapp.kotlin.activities.ui.providers.providerschat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vikendu.theservicesapp.R

class ProvidersChatContactFragment : Fragment() {

    companion object {
        fun newInstance() = ProvidersChatContactFragment()
    }

    private lateinit var viewModel: ProvidersChatContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_providers_chat_contact, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProvidersChatContactViewModel::class.java)
        // TODO: Use the ViewModel
    }

}