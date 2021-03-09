package com.jarvizu.geotopic.api

import com.jarvizu.geotopic.data.EventResponse
import retrofit2.Response
import javax.inject.Inject

class APIHelperImpl @Inject constructor(
    private val apiService: APIService
) : APIHelper {
    override suspend fun getEvents(
        key: String,
        location: String,
        radius: String,
        keyword: String,
        sort: String,
    ): Response<EventResponse> {
        return apiService.getEvents(key, location, radius, keyword, sort)
    }
}