package com.jarvizu.geotopic.ui.main

import com.jarvizu.geotopic.data.PlaceResponse
import retrofit2.Response

interface APIHelper {
    suspend fun getPlaces(key: String, location: String, radius: String, query: String, fields: String):
            Response<PlaceResponse>
}