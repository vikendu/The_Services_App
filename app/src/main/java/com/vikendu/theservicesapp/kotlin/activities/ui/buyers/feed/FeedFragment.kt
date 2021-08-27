package com.vikendu.theservicesapp.kotlin.activities.ui.buyers.feed

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.activities.ProviderDetailsActivity
import com.vikendu.theservicesapp.adapters.AdCardAdapter
import com.vikendu.theservicesapp.databinding.FragmentFeedBinding
import com.vikendu.theservicesapp.models.Advert
import com.vikendu.theservicesapp.utils.RecyclerItemClickListener

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val feedViewModel = FeedViewModel()
    private var advertArray: ArrayList<Advert> = ArrayList()

    private val REQUEST_CODE = 100
    private val MIN_TIME = 5000
    private val MIN_DISTANCE = 1000

    lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        if(ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
                        REQUEST_CODE)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false)
        val rootView = binding.root

        feedViewModel.getFeedAds()?.observe(viewLifecycleOwner, {
            advertArray = it as ArrayList<Advert>
            updateFeed(it)
        })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.idRVAdCreation.addOnItemTouchListener(RecyclerItemClickListener(
            context,
            binding.idRVAdCreation,
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
        binding.idRVAdCreation.layoutManager = linearLayoutManager
        binding.idRVAdCreation.adapter = adCardAdapter
    }

//    private fun getCurrentLocation(): Array<String> {
//        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//
//    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
//            thetext.text = ("" + location.longitude + ":" + location.latitude)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

        }
        override fun onProviderEnabled(provider: String) {

        }
        override fun onProviderDisabled(provider: String) {

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        feedViewModel.removeListener()
        super.onDestroy()
    }
}
