package com.vikendu.theservicesapp.kotlin.activities.ui.providers.data

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.adapters.AdCardAdapter
import com.vikendu.theservicesapp.databinding.FragmentApprovedBinding
import com.vikendu.theservicesapp.models.Advert

class ApprovedFragment : Fragment(R.layout.fragment_approved) {
    private var _binding: FragmentApprovedBinding? = null
    private val binding get() = _binding!!

    private val providersAdViewmodel = ProviderAdViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApprovedBinding.inflate(inflater, container, false)
        val rootView = binding.root

        providersAdViewmodel.getAllAds()?.observe(viewLifecycleOwner, {
            updateFeed(sanitizeArray(it as ArrayList<Advert>))
        })
        return rootView
    }

    private fun sanitizeArray(arrayList: ArrayList<Advert>): ArrayList<Advert> {
        var resultantArrayList = ArrayList<Advert>()
        arrayList.forEach { i -> if (i.isApproved) { resultantArrayList.add(i) } }
        return resultantArrayList
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