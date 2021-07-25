package com.vikendu.theservicesapp.kotlin.activities.ui.providers.localfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.adapters.AdCardAdapter
import com.vikendu.theservicesapp.models.Advert
import kotlinx.android.synthetic.main.fragment_feed.*

class LocalFeedFragment : Fragment(R.layout.fragment_local_feed) {
    private val localFeedViewModel = LocalFeedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        localFeedViewModel.getAds()?.observe(viewLifecycleOwner, {
            updateFeed(it as ArrayList<Advert>)
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun updateFeed(advertList: ArrayList<Advert>) {
        val adCardAdapter = AdCardAdapter(context, advertList)
        val linearLayoutManager = LinearLayoutManager(context)
        idRVAdCreation?.layoutManager = linearLayoutManager
        idRVAdCreation?.adapter = adCardAdapter
    }

    override fun onDestroy() {
        localFeedViewModel.removeListener()
        super.onDestroy()
    }
}