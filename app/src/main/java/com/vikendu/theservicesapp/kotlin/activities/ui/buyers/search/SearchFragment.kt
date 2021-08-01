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
    private var queryResults: ArrayList<Advert> = ArrayList()
    private var allResults = ArrayList<Advert>()
    private var query: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        query = arguments?.getString("query")
        query?.let { Log.d("QUERY!!!!", query!!) }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        searchViewModel.getAllAds()?.observe(viewLifecycleOwner, {
            allResults = it as ArrayList<Advert>
        })

        query?.let { Log.d("Query before Observer", it) }
        Log.d("size of result", allResults.size.toString())
        searchViewModel.search(query!!, allResults).observe(viewLifecycleOwner, {
                updateFeed(it as ArrayList<Advert>)
        })

        return binding.root
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
        searchViewModel.removeListener()
        super.onDestroy()
    }
}