package com.jarvizu.geotopic.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarvizu.geotopic.data.EventResponse
import com.jarvizu.geotopic.repository.MainRepository
import com.jarvizu.geotopic.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {


    private val _resource = MutableLiveData<Resource<EventResponse>>()

    val resource: LiveData<Resource<EventResponse>>
        get() = _resource

    // Store rest paramets in a lifecycle aware singleton
    fun setRepositoryParameters(location: String, radius: String, keyword: String) = mainRepository
        .setRestParameters(location, radius, keyword)

    fun addEventMarker(latitude: String?, longitude: String?, venueTitle: String?) {
        mainRepository.locationMarkers.add(
            MainRepository.Location(
                latitude.toString(), longitude.toString(),
                venueTitle.toString()
            )
        )
    }


    fun getEvents() = viewModelScope.launch {
        _resource.postValue(Resource.loading(null))
        mainRepository.getEvents().let {
            if (it.isSuccessful) {
                _resource.postValue(Resource.success(it.body()))
            } else {
                _resource.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
}