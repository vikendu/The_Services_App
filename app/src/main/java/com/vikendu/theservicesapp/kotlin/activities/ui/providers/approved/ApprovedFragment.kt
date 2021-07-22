package com.vikendu.theservicesapp.kotlin.activities.ui.providers.approved

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vikendu.theservicesapp.R

class ApprovedFragment : Fragment() {

    companion object {
        fun newInstance() = ApprovedFragment()
    }

    private lateinit var viewModel: ApprovedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_approved, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ApprovedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}