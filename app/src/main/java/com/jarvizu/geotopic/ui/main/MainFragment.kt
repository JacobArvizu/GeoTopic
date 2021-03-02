package com.jarvizu.geotopic.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.jarvizu.geotopic.R
import com.jarvizu.geotopic.databinding.MainFragmentBinding
import com.jarvizu.geotopic.utils.Constants
import es.dmoral.toasty.Toasty
import timber.log.Timber
import java.util.*


class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private lateinit var currentPlace: Place

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        Places.initialize(requireContext(), Constants.API_KEY, Locale.US)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //binding.txtQueryInput.isEnabled = false

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment


        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Toasty.success(requireActivity(), place.name.toString(), Toast.LENGTH_SHORT).show()
                Timber.d("%s selected", place.name.toString())
                binding.txtCity.text = place.name
                currentPlace = place
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
            }
        })

        with(binding) {

            fabSearch.setOnClickListener {
                Timber.d("Fab pressed")
                if (txtQueryInput.text != null) {
                    try {
                        Toasty.success(
                            requireActivity(),
                            "Searching for " + txtQueryInput.text + " within " + numberPicker.progress + "mi radius of " +
                                    currentPlace.name
                                        .toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().navigate(R.id.action_mainFragment_to_topics)
                    } catch (exception: Exception) {
                        Toasty.error(requireActivity(), "Place/City not selected!", Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    Toasty.error(requireActivity(), "Query empty!", Toast.LENGTH_LONG).show()

                }
            }


        }
    }
}