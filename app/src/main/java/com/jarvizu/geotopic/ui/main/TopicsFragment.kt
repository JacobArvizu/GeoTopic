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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.jarvizu.geotopic.data.EventItem
import com.jarvizu.geotopic.databinding.TopicsFragmentBinding
import com.jarvizu.geotopic.utils.Constants
import com.jarvizu.geotopic.utils.Status
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class TopicsFragment : Fragment() {

    // Navigation arguments received as a parcable
    private val safeArguments by navArgs<TopicsFragmentArgs>()

    // Safe binding using viewbinding
    private var _binding: TopicsFragmentBinding? = null

    private val binding get() = _binding!!

    // Use shared viewmodel injected with Hilt
    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using viewbinding
        _binding = TopicsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        Places.initialize(requireContext(), Constants.PLACE_API_KEY, Locale.US)
        viewModel.getEvents(safeArguments.navArgs)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //create the ItemAdapter holding your Items
        val itemAdapter = ItemAdapter<EventItem>()
        val fastAdapter = FastAdapter.with(itemAdapter)
        // Observe the returned resource from ViewModel
        startObserving(itemAdapter, fastAdapter)
        // Open link if clicked
        onClickItem(fastAdapter)
    }

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


    private fun startObserving(itemAdapter: ItemAdapter<EventItem>, fastAdapter: FastAdapter<EventItem>) {
        viewModel.resource.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { resource ->
                        Timber.d(resource.toString())
                        binding.rvEvents.apply {
                            setHasFixedSize(true)
                            adapter = fastAdapter
                            layoutManager = LinearLayoutManager(requireActivity())

                            if (resource?.page?.totalElements == 0) {
                                Toasty.info(requireActivity(), "No events found in radius", Toast.LENGTH_LONG)
                            } else {
                                // For each result safe call and display to adapter
                                resource?.embedded?.events?.forEach { event ->
                                    val eventItem = EventItem()
                                    eventItem.name = event?.name
                                    event?.embedded?.venues?.forEach { venue ->
                                        eventItem.address = venue?.address?.line1.toString()
                                    }
                                    eventItem.date = event?.dates?.start?.localDate
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