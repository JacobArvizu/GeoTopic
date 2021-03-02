package com.jarvizu.geotopic.ui.main

import com.jarvizu.geotopic.data.PlaceResponse
import retrofit2.Response
import javax.inject.Inject

class APIHelperImpl @Inject constructor(
    private val apiService: APIService
) : APIHelper {
    override suspend fun getPlaces(
        key: String,
        location: String,
        radius: String,
        query: String,
        fields: String
    ): Response<PlaceResponse> {
        return apiService.getPlaces(key, location, radius, query, fields)
    }
}