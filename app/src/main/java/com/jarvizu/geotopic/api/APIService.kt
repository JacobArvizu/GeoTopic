package com.jarvizu.geotopic.api

import com.jarvizu.geotopic.data.EventResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    /*
     * Example: Makes an REST GET request for events with the required parameters
     * https://app.ticketmaster.com/discovery/v2/events.json?keyword=music&latlong=37.931870,-121.695786&radius=300&sort=date,asc&apikey=m7G79jCaevtO8lOxoBmkQrZQuJLYPue3
     *
     */

    @GET("/discovery/v2/events.json?")
    suspend fun getEvents(
        @Query("apikey") key: String,
        @Query("latlong") location: String,
        @Query("radius") radius: String,
        @Query("keyword") keyword: String,
        @Query("sort") sort: String,
    ): Response<EventResponse>
}