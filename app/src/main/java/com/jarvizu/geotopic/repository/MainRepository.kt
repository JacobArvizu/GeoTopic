package com.jarvizu.geotopic.repository

import com.jarvizu.geotopic.ui.main.APIHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: APIHelper
) {

    suspend fun getPlaces(key: String, location: String, radius: String, query: String, fields: String) =
        apiHelper.getPlaces(key, location, radius, query, fields)
}