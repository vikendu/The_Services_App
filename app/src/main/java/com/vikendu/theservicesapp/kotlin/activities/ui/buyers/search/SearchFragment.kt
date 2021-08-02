package com.vikendu.theservicesapp.kotlin.activities.ui.buyers.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.adapters.AdCardAdapter
import com.vikendu.theservicesapp.databinding.FragmentSearchBinding
import com.vikendu.theservicesapp.models.Advert

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel = SearchViewModel()
    private var allResults = ArrayList<Advert>()
    private var query: String? = null

    override fun onStart() {
        super.onStart()
        query = arguments?.getString("query")

        if (query != null) {
            searchViewModel.getAllAds()?.observe(viewLifecycleOwner, { item ->
                allResults = item as ArrayList<Advert>
                searchViewModel
                    .search(query!!, allResults)
                    .observe(viewLifecycleOwner, {
                    updateFeed(it as ArrayList<Advert>)
                })
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun updateFeed(advertList: ArrayList<Advert>) {
        val adCardAdapter = AdCardAdapter(context, advertList)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.idRVAdCreation.layoutManager = linearLayoutManager
        binding.idRVAdCreation.adapter = adCardAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroyCalled", "yes")
    }
}