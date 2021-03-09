package com.jarvizu.geotopic.api

import com.jarvizu.geotopic.data.EventResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/discovery/v2/events.json?")
    suspend fun getEvents(
        @Query("apikey") key: String,
        @Query("latlong") location: String,
        @Query("radius") radius: String,
        @Query("keyword") keyword: String,
    ): Response<EventResponse>
}