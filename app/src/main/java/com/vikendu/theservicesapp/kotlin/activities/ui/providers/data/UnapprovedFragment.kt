package com.vikendu.theservicesapp.kotlin.activities.ui.providers.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.adapters.AdCardAdapter
import com.vikendu.theservicesapp.databinding.FragmentUnapprovedBinding
import com.vikendu.theservicesapp.models.Advert

class UnapprovedFragment : Fragment(R.layout.fragment_unapproved) {
    private var _binding: FragmentUnapprovedBinding? = null
    private val binding get() = _binding!!

    private val providersAdViewmodel = ProviderAdViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUnapprovedBinding.inflate(inflater, container, false)
        val rootView = binding.root

        providersAdViewmodel.getAllAds()?.observe(viewLifecycleOwner, {
            updateFeed(providersAdViewmodel.getUnapprovedAds(it as ArrayList<Advert>))
        })
        return rootView
    }

    private fun updateFeed(advertList: ArrayList<Advert>) {
        val adCardAdapter = AdCardAdapter(context, advertList)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.idRVAdCreation.layoutManager = linearLayoutManager
        binding.idRVAdCreation.adapter = adCardAdapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}