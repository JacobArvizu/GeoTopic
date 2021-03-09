package com.jarvizu.geotopic.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.jarvizu.geotopic.R
import com.jarvizu.geotopic.data.NavArgs
import com.jarvizu.geotopic.databinding.MainFragmentBinding
import com.jarvizu.geotopic.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private lateinit var currentPlace: Place

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        Places.initialize(requireContext(), Constants.PLACE_API_KEY, Locale.US)
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
                    Toasty.success(requireActivity(), place.name.toString(), Toast.LENGTH_LONG).show()
                    Timber.d("%s selected", place.name.toString())
                    txtCity.text = "Searching near:\n${place.address}"
                    currentPlace = place
                }

                override fun onError(status: Status) {
                    Toasty.error(requireActivity(), status.statusMessage, Toast.LENGTH_LONG).show()
                }
            })


            fabSearch.setOnClickListener {
                Timber.d("Fab pressed")
                if (txtQueryInput.text != null) {
                    try {
                        Toasty.success(
                            requireActivity(),
                            "Searching for " + txtQueryInput.text + " within " + numberPicker.progress + "mi radius of " +
                                    currentPlace.name
                                        .toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        // Navigate passing Radius
                        val latLong: String = currentPlace.latLng?.latitude.toString() + "," + currentPlace.latLng
                            ?.longitude.toString()
                        Timber.d(latLong)
                        // Build navArgs for retrofit http request and pass as navArg
                        val action = MainFragmentDirections.actionMainFragmentToTopics(
                            NavArgs(
                                latLong, numberPicker.progress.toString(), txtQueryInput.text.toString()
                            )
                        )
                        findNavController().navigate(action)
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