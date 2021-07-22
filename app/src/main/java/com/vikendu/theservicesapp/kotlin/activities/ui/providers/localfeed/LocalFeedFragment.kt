package com.vikendu.theservicesapp.kotlin.activities.ui.providers.localfeed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vikendu.theservicesapp.R

class LocalFeedFragment : Fragment() {

    companion object {
        fun newInstance() = LocalFeedFragment()
    }

    private lateinit var viewModel: LocalFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_local_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LocalFeedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}