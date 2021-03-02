package com.jarvizu.geotopic.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.jarvizu.geotopic.data.NavArgs
import com.jarvizu.geotopic.data.PlaceItem
import com.jarvizu.geotopic.data.PlacePojo
import com.jarvizu.geotopic.databinding.TopicsFragmentBinding
import com.jarvizu.geotopic.utils.Constants
import com.jarvizu.geotopic.utils.ServiceBuilder
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*


class Topics : Fragment() {

    private val safeArguments by navArgs<TopicsArgs>()

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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        makePlaceRequest()
    }

    /*
        Make place requsest using safeArgs passed with navigation component
     */
    private fun makePlaceRequest() {

        val args = safeArguments.navArgs

        val request = ServiceBuilder.buildService(APIService::class.java)
        val call = request.getPlaces(Constants.API_KEY,args.location, args.radius,args.query, "formatted_address," +
                "name,,formatted_address")

        call.enqueue(object : Callback<PlacePojo> {
            override fun onResponse(call: Call<PlacePojo>, response: Response<PlacePojo>) {
                if (response.isSuccessful) {
                    Timber.d(response.message())
                    binding.rvPlaces.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(requireActivity())
                        //create the ItemAdapter holding your Items
                        val itemAdapter = ItemAdapter<PlaceItem>()
                        val fastAdapter = FastAdapter.with(itemAdapter)

                        adapter = fastAdapter


                        response.body()?.results?.forEach {
                            val placeItem = PlaceItem()
                            placeItem.name = it?.name
                            placeItem.address = it?.formattedAddress
                            itemAdapter.add(placeItem)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<PlacePojo>, t: Throwable) {
                Toasty.error(requireActivity(), "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}