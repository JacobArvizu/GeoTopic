package com.jarvizu.geotopic.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarvizu.geotopic.data.NavArgs
import com.jarvizu.geotopic.data.PlaceResponse
import com.jarvizu.geotopic.repository.MainRepository
import com.jarvizu.geotopic.utils.Constants
import com.jarvizu.geotopic.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _resource = MutableLiveData<Resource<PlaceResponse>>()


    val resource: LiveData<Resource<PlaceResponse>>
        get() = _resource


    public fun getPlaces(navArgs: NavArgs) = viewModelScope.launch {
        _resource.postValue(Resource.loading(null))
        mainRepository.getPlaces(
            Constants.API_KEY, navArgs.location, navArgs.radius, navArgs.query, "formatted_address," +
                    "name,place_id"
        ).let {
            if (it.isSuccessful) {
                _resource.postValue(Resource.success(it.body()))
            } else {
                _resource.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
}