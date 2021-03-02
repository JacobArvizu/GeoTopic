package com.jarvizu.geotopic.ui.main

import com.jarvizu.geotopic.data.PlacePojo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/maps/api/place/textsearch/json?inputtype=textquery")
    fun getPlaces(
        @Query("key") key: String,
        @Query("location") location: String,
        @Query("radius") radius: String,
        @Query("query") query: String,
        @Query("fields") fields: String
    ): Call<PlacePojo>
}