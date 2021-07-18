package com.vikendu.theservicesapp.kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.adapters.AdCardAdapter
import com.vikendu.theservicesapp.models.Advert
import com.vikendu.theservicesapp.viewmodels.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private val feedViewModel = FeedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        feedViewModel.feedAds.observe(viewLifecycleOwner, Observer {
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

    //TODO: Add a listener to go to ProvidersDetailsActivity when AD is touched
}
