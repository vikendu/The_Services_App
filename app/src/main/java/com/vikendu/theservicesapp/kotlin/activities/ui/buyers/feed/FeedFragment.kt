package com.vikendu.theservicesapp.kotlin.activities.ui.buyers.feed

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private val MIN_TIME: Float = 5000.0F
    private val MIN_DISTANCE: Long = 1000

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    lateinit var locationManager: LocationManager

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.d(TAG, "${it.key} = ${it.value}")
            }
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                Log.d(TAG, "Permission granted")
            } else {
                Log.d(TAG, "Permission not granted")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        if(ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
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
        //TODO: temporarily updating location from here
        getCurrentLocation()
        Log.d("Latitude", latitude.toString())
        Log.d("Longitude", longitude.toString())

        val adCardAdapter = AdCardAdapter(context, advertList)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.idRVAdCreation.layoutManager = linearLayoutManager
        binding.idRVAdCreation.adapter = adCardAdapter
    }

    private fun getCurrentLocation() {
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        //var networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (gpsEnabled) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION),
                        REQUEST_CODE)
                //TODO: onRequestPermissionResult is deprecated;
                // implementing registerForActivityResult [#58] [https://github.com/vikendu/The_Services_App/issues/58#issuecomment-908874144]
                checkPermissions()
                return
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_DISTANCE,
                MIN_TIME,
                object : LocationListener {
                    override fun onLocationChanged(p0: Location) {
                        //TODO: ("Not yet implemented")
                        longitude = p0.longitude
                        latitude =  p0.latitude
                    }
                })
        }
    }

    private fun checkPermissions() {
        if (requireContext().let {
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)
            } != PackageManager.PERMISSION_GRANTED) {
            //Log.d(TAG, "Request Permissions")
            requestMultiplePermissions.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        } else {
            //Log.d(TAG, "Permission Already Granted")
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