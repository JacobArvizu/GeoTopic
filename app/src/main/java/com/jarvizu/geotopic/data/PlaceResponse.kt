package com.jarvizu.geotopic.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*
    Place Pojo
 */

@JsonClass(generateAdapter = true)
data class PlaceResponse(
    @Json(name = "html_attributions")
    val htmlAttributions: List<Any?>? = listOf(),
    @Json(name = "next_page_token")
    val nextPageToken: String? = "",
    @Json(name = "results")
    val results: List<Result?>? = listOf(),
    @Json(name = "status")
    val status: String? = ""
) {
    @JsonClass(generateAdapter = true)
    data class Result(
        @Json(name = "formatted_address")
        val formattedAddress: String? = "",
        @Json(name = "name")
        val name: String? = "",
        @Json(name = "place_id")
        val placeId: String? = ""
    ) {

        @JsonClass(generateAdapter = true)
        data class OpeningHours(
            @Json(name = "open_now")
            val openNow: Boolean? = false
        )

        @JsonClass(generateAdapter = true)
        data class Photo(
            @Json(name = "height")
            val height: Int? = 0,
            @Json(name = "html_attributions")
            val htmlAttributions: List<String?>? = listOf(),
            @Json(name = "photo_reference")
            val photoReference: String? = "",
            @Json(name = "width")
            val width: Int? = 0
        )

        @JsonClass(generateAdapter = true)
        data class PlusCode(
            @Json(name = "compound_code")
            val compoundCode: String? = "",
            @Json(name = "global_code")
            val globalCode: String? = ""
        )
    }
}