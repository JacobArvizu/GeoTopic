package com.jarvizu.geotopic.api

import com.jarvizu.geotopic.data.EventResponse
import retrofit2.Response

interface APIHelper {
    suspend fun getEvents(key: String, location: String, radius: String, keyword: String):
            Response<EventResponse>
}