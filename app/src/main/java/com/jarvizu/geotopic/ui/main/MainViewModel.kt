package com.jarvizu.geotopic.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarvizu.geotopic.data.EventResponse
import com.jarvizu.geotopic.data.NavArgs
import com.jarvizu.geotopic.repository.MainRepository
import com.jarvizu.geotopic.utils.Constants
import com.jarvizu.geotopic.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _resource = MutableLiveData<Resource<EventResponse>>()


    val resource: LiveData<Resource<EventResponse>>
        get() = _resource


    fun getEvents(navArgs: NavArgs) = viewModelScope.launch {
        _resource.postValue(Resource.loading(null))
        mainRepository.getEvents(
            Constants.TICKETMASTER_API_KEY, navArgs.location, navArgs.radius, navArgs.keyword
        ).let {
            if (it.isSuccessful) {
                _resource.postValue(Resource.success(it.body()))
            } else {
                _resource.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
}