package com.jarvizu.geotopic.repository

import com.jarvizu.geotopic.api.APIHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: APIHelper
) {

    suspend fun getEvents(key: String, location: String, radius: String, query: String) =
        apiHelper.getEvents(key, location, radius, query)
}