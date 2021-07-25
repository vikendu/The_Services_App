package com.vikendu.theservicesapp.kotlin.activities.ui.providers.localfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.adapters.AdCardAdapter
import com.vikendu.theservicesapp.databinding.FragmentLocalFeedBinding
import com.vikendu.theservicesapp.models.Advert

class LocalFeedFragment : Fragment(R.layout.fragment_local_feed) {
    private var _binding: FragmentLocalFeedBinding? = null
    private val binding get() = _binding!!

    private val localFeedViewModel = LocalFeedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocalFeedBinding.inflate(inflater, container, false)
        val rootView = binding.root

        localFeedViewModel.getAds()?.observe(viewLifecycleOwner, {
            updateFeed(it as ArrayList<Advert>)
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

    override fun onDestroy() {
        localFeedViewModel.removeListener()
        super.onDestroy()
    }
}