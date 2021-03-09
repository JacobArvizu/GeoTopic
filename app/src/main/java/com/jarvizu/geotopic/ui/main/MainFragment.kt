package com.jarvizu.geotopic.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jarvizu.geotopic.R
import com.jarvizu.geotopic.databinding.MainFragmentBinding
import com.jarvizu.geotopic.utils.Constants
import com.skydoves.whatif.*
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class MainFragment : Fragment() {

    // Safely declare a viewbinding
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentPlace: Place
    private lateinit var latLong: String

    // Use shared viewmodel injected with Hilt
    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        Places.initialize(requireContext(), Constants.PLACE_API_KEY, Locale.US)
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.GONE
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        /*
         * Bind all UI elements to var binding
         */
        with(binding) {

            // Autocomplete Fragment
            val autocompleteFragment =
                childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
            autocompleteFragment.setPlaceFields(
                listOf(
                    Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place
                        .Field.ADDRESS
                )
            )
            autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    currentPlace = place
                    Timber.d("%s selected", currentPlace.name.toString())
                    txtCity.text = "Searching near:\n${currentPlace.address}"
                    currentPlace.latLng.whatIfNotNull { it ->
                        latLong = it.latitude.toString() + "," + it.longitude.toString()
                    }
                }

                override fun onError(status: Status) {
                    Toasty.error(requireActivity(), status.statusMessage, Toast.LENGTH_LONG).show()
                }
            })


            fabSearch.setOnClickListener {
                if (txtQueryInput.text.isNullOrEmpty()) {
                    Toasty.error(requireActivity(), "No search query entered", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                try {
                    // Make sure the place was selected and has a valid lat/long coordinates
                    latLong =
                        currentPlace.latLng?.latitude.toString() + "," + currentPlace.latLng?.longitude.toString()
                    // Build a parcelized argument to that will be passed to a REST Call
                    val bundle = bundleOf(
                        "location" to latLong, "radius" to numberPicker.progress.toString(),
                        "keyword" to txtQueryInput.text.toString()
                    )
                    viewModel.setRepositoryParameters(
                        latLong,
                        numberPicker.progress.toString(),
                        txtQueryInput.text.toString()
                    )
                    findNavController().navigate(R.id.action_mainFragment_to_topics, bundle)
                } catch (e: Exception) {
                    Toasty.error(requireActivity(), "Error getting location coordinates", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}


