package com.jarvizu.geotopic.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jarvizu.geotopic.R
import com.jarvizu.geotopic.databinding.MapFragmentBinding
import com.jarvizu.geotopic.repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment() {

    @Inject // Use dagger to inject a singleton refrence of the repository
    lateinit var mainRepository: MainRepository

    private lateinit var mMap: GoogleMap
    private var mapReady = false
    private var _binding: MapFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = MapFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        // Async update map
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            mapReady = true
            updateMap()
        }
        return view
    }

    private fun updateMap() {
        if (mapReady) {
            // Use the injected dependency and add markers when the map is ready to recieve the data
            mainRepository.locationMarkers.forEach {
                val marker = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                mMap.addMarker(MarkerOptions().position(marker).title(it.venueTitle))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
            }
        }
    }
}

