package com.vikendu.theservicesapp.kotlin.activities.ui.buyers.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

//        binding.fab.setOnClickListener{ view ->
//            this.onSearchRequested()
//
//        }

        return binding.root
    }

//    private fun onSearchRequested(): ArrayList<Advert> {
//        if (Intent.ACTION_SEARCH == intent.action) {
//            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
//                //TODO: here call the search function
//
//            }
//        }
//    }
}