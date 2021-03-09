package com.jarvizu.geotopic.ui.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jarvizu.geotopic.R
import com.jarvizu.geotopic.data.EventItem
import com.jarvizu.geotopic.databinding.TopicsFragmentBinding
import com.jarvizu.geotopic.utils.Status
import com.jarvizu.geotopic.utils.getStringFormatted
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import timber.log.Timber


@AndroidEntryPoint
class TopicsFragment : Fragment() {

    // Safe binding using viewbinding
    private var _binding: TopicsFragmentBinding? = null
    private val binding get() = _binding!!

    // Use shared viewmodel injected with Hilt
    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using viewbinding
        _binding = TopicsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE
        //create the ItemAdapter holding your Items
        val itemAdapter = ItemAdapter<EventItem>()
        val fastAdapter = FastAdapter.with(itemAdapter)
        // Observe the returned resource from ViewModel
        startObserving(itemAdapter, fastAdapter)
        viewModel.getEvents()
        // Open link if clicked
        onClickItem(fastAdapter)
    }

    // View binding click binding to adapter
    private fun onClickItem(fastAdapter: FastAdapter<EventItem>) {
        fastAdapter.onClickListener = { view, adapter, item, position ->
            Toasty.info(requireContext(), "Opening link", Toast.LENGTH_SHORT).show()
            val urlString = item.linkUrl
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.android.chrome")
            try {
                requireActivity().startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null)
                requireActivity().startActivity(intent)
            }
            false
        }
    }


    /*
     * Observer that gets a sorted list of events then binds it to a recyclerview and adapter
     */
    private fun startObserving(itemAdapter: ItemAdapter<EventItem>, fastAdapter: FastAdapter<EventItem>) {

        viewModel.resource.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Timber.d("Status" + it.status)
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { resource ->
                        Timber.d(resource.toString())
                        binding.rvEvents.apply {
                            setHasFixedSize(true)
                            adapter = fastAdapter
                            layoutManager = LinearLayoutManager(requireActivity())
                            Timber.d("Elements found%s", resource?.page?.totalElements)
                            if (resource?.page?.totalElements == 0) {
                                Toasty.info(requireActivity(), "No events found in radius", Toast.LENGTH_LONG).show()
                            } else {
                                // For each result safe call and display to adapter
                                resource?.embedded?.events?.forEach { event ->
                                    val eventItem = EventItem()
                                    eventItem.name = event?.name
                                    event?.embedded?.venues?.forEach { venue ->
                                        eventItem.venueName = venue?.name
                                        eventItem.address = venue?.address?.line1.toString()
                                        viewModel.addEventMarker(
                                            venue?.location?.latitude,
                                            venue?.location?.longitude,
                                            venue?.name,
                                            event.name
                                        )
                                    }
                                    eventItem.date = getStringFormatted(event?.dates?.start?.localDate)
                                    event?.images?.forEach { image ->
                                        eventItem.photo = image?.url
                                    }
                                    eventItem.linkUrl = event?.url
                                    itemAdapter.add(eventItem)
                                }
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