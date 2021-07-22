package com.vikendu.theservicesapp.kotlin.activities.ui.providers.unapproved

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vikendu.theservicesapp.R

class UnapprovedFragment : Fragment() {

    companion object {
        fun newInstance() = UnapprovedFragment()
    }

    private lateinit var viewModel: UnapprovedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_unapproved, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UnapprovedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}