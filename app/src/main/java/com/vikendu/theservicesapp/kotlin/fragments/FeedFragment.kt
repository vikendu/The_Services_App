package com.vikendu.theservicesapp.kotlin.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.activities.ProviderDetailsActivity
import com.vikendu.theservicesapp.adapters.AdCardAdapter
import com.vikendu.theservicesapp.models.Advert
import com.vikendu.theservicesapp.utils.RecyclerItemClickListener
import com.vikendu.theservicesapp.viewmodels.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private val feedViewModel = FeedViewModel()
    private var advertArray: ArrayList<Advert> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedViewModel.feedAds.observe(viewLifecycleOwner, {
            advertArray = it as ArrayList<Advert>
            updateFeed(it)
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        idRVAdCreation.addOnItemTouchListener(RecyclerItemClickListener(
            context,
            idRVAdCreation,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val intent = Intent(view?.context, ProviderDetailsActivity::class.java)
                    intent.putExtra("advert", advertArray[position])
                    startActivity(intent)
                }

                override fun onLongItemClick(view: View?, position: Int) {
                    TODO("Add option to report and what not")
                }

            }))
        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateFeed(advertList: ArrayList<Advert>) {
        val adCardAdapter = AdCardAdapter(context, advertList)
        val linearLayoutManager = LinearLayoutManager(context)
        idRVAdCreation?.layoutManager = linearLayoutManager
        idRVAdCreation?.adapter = adCardAdapter
    }

    override fun onDestroy() {
        feedViewModel.removeListener()
        super.onDestroy()
    }
    //TODO: Add a listener to go to ProvidersDetailsActivity when AD is touched
}
