package com.jarvizu.geotopic.repository

import com.jarvizu.geotopic.api.APIHelper
import com.jarvizu.geotopic.repository.MainRepository.Parameters.keyword
import com.jarvizu.geotopic.repository.MainRepository.Parameters.location
import com.jarvizu.geotopic.repository.MainRepository.Parameters.radius
import com.jarvizu.geotopic.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

/*
 * Repository that connects to remote data source
 * the repository is instantiated as a singleton and provided wither dagger
 */
@Singleton
class MainRepository @Inject constructor(
    private val apiHelper: APIHelper
) {

    data class Location(val latitude: String, val longitude: String, val venueTitle: String)

    val locationMarkers: MutableList<Location> = mutableListOf()

    object Parameters {
        var location: String = "0,0"
        var radius: String = "0"
        var keyword: String = ""
    }

    suspend fun getEvents() =
        apiHelper.getEvents(Constants.TICKETMASTER_API_KEY, location, radius, keyword, "date,asc")

    fun setRestParameters(location: String, radius: String, keyword: String) {
        Parameters.location = location
        Parameters.radius = radius
        Parameters.keyword = keyword
    }
}