package com.jarvizu.geotopic.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.material.snackbar.Snackbar
import com.jarvizu.geotopic.data.PlaceItem
import com.jarvizu.geotopic.databinding.TopicsFragmentBinding
import com.jarvizu.geotopic.utils.Constants
import com.jarvizu.geotopic.utils.Status
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import es.dmoral.toasty.Toasty.success
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class TopicsFragment : Fragment() {

    private val safeArguments by navArgs<TopicsFragmentArgs>()

    private var _binding: TopicsFragmentBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = TopicsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        Places.initialize(requireContext(), Constants.API_KEY, Locale.US)
        viewModel.getPlaces(safeArguments.navArgs)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.resource.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when(it.status){
                Status.SUCCESS -> {
                    it.data.let {resource->
                        Timber.d(resource.toString())
                        binding.rvPlaces.apply {
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(requireActivity())
                            //create the ItemAdapter holding your Items
                            val itemAdapter = ItemAdapter<PlaceItem>()
                            val fastAdapter = FastAdapter.with(itemAdapter)

                            adapter = fastAdapter


                            resource?.results?.forEach() {
                                val placeItem = PlaceItem()
                                placeItem.name = it?.name
                                placeItem.address = it?.formattedAddress
                                itemAdapter.add(placeItem)
                            }
                        }
                    }
                }
                Status.LOADING -> {
                    Timber.d("Loading data...")
                }
                Status.ERROR -> {
                    Toasty.error(requireActivity(), "Error" + Status.ERROR, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}