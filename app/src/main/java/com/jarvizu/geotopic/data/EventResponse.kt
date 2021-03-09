package com.jarvizu.geotopic.data


import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("_embedded")
    val embedded: Embedded?,
    @SerializedName("_links")
    val links: Links?,
    @SerializedName("page")
    val page: Page?
) {
    data class Embedded(
        @SerializedName("events")
        val events: List<Event?>?
    ) {
        data class Event(
            @SerializedName("dates")
            val dates: Dates?,
            @SerializedName("description")
            val description: String?,
            @SerializedName("distance")
            val distance: Double?,
            @SerializedName("_embedded")
            val embedded: Embedded?,
            @SerializedName("id")
            val id: String?,
            @SerializedName("images")
            val images: List<Image?>?,
            @SerializedName("_links")
            val links: Links?,
            @SerializedName("locale")
            val locale: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("sales")
            val sales: Sales?,
            @SerializedName("test")
            val test: Boolean?,
            @SerializedName("type")
            val type: String?,
            @SerializedName("units")
            val units: String?,
            @SerializedName("url")
            val url: String?
        ) {
            data class Dates(
                @SerializedName("access")
                val access: Access?,
                @SerializedName("end")
                val end: End?,
                @SerializedName("spanMultipleDays")
                val spanMultipleDays: Boolean?,
                @SerializedName("start")
                val start: Start?,
                @SerializedName("status")
                val status: Status?,
                @SerializedName("timezone")
                val timezone: String?
            ) {
                data class Access(
                    @SerializedName("endApproximate")
                    val endApproximate: Boolean?,
                    @SerializedName("endDateTime")
                    val endDateTime: String?,
                    @SerializedName("startApproximate")
                    val startApproximate: Boolean?,
                    @SerializedName("startDateTime")
                    val startDateTime: String?
                )

                data class End(
                    @SerializedName("approximate")
                    val approximate: Boolean?,
                    @SerializedName("dateTime")
                    val dateTime: String?,
                    @SerializedName("localTime")
                    val localTime: String?,
                    @SerializedName("noSpecificTime")
                    val noSpecificTime: Boolean?
                )

                data class Start(
                    @SerializedName("dateTBA")
                    val dateTBA: Boolean?,
                    @SerializedName("dateTBD")
                    val dateTBD: Boolean?,
                    @SerializedName("dateTime")
                    val dateTime: String?,
                    @SerializedName("localDate")
                    val localDate: String?,
                    @SerializedName("localTime")
                    val localTime: String?,
                    @SerializedName("noSpecificTime")
                    val noSpecificTime: Boolean?,
                    @SerializedName("timeTBA")
                    val timeTBA: Boolean?
                )

                data class Status(
                    @SerializedName("code")
                    val code: String?
                )
            }

            data class Embedded(
                @SerializedName("venues")
                val venues: List<Venue?>?
            ) {
                data class Venue(
                    @SerializedName("address")
                    val address: Address?,
                    @SerializedName("city")
                    val city: City?,
                    @SerializedName("country")
                    val country: Country?,
                    @SerializedName("distance")
                    val distance: Double?,
                    @SerializedName("dmas")
                    val dmas: List<Dma?>?,
                    @SerializedName("id")
                    val id: String?,
                    @SerializedName("images")
                    val images: List<Image?>?,
                    @SerializedName("_links")
                    val links: Links?,
                    @SerializedName("locale")
                    val locale: String?,
                    @SerializedName("location")
                    val location: Location?,
                    @SerializedName("markets")
                    val markets: List<Market?>?,
                    @SerializedName("name")
                    val name: String?,
                    @SerializedName("postalCode")
                    val postalCode: String?,
                    @SerializedName("state")
                    val state: State?,
                    @SerializedName("test")
                    val test: Boolean?,
                    @SerializedName("timezone")
                    val timezone: String?,
                    @SerializedName("type")
                    val type: String?,
                    @SerializedName("units")
                    val units: String?,
                    @SerializedName("upcomingEvents")
                    val upcomingEvents: UpcomingEvents?,
                    @SerializedName("url")
                    val url: String?
                ) {
                    data class Address(
                        @SerializedName("line1")
                        val line1: String?
                    )

                    data class City(
                        @SerializedName("name")
                        val name: String?
                    )

                    data class Country(
                        @SerializedName("countryCode")
                        val countryCode: String?,
                        @SerializedName("name")
                        val name: String?
                    )

                    data class Dma(
                        @SerializedName("id")
                        val id: Int?
                    )

                    data class Image(
                        @SerializedName("attribution")
                        val attribution: String?,
                        @SerializedName("fallback")
                        val fallback: Boolean?,
                        @SerializedName("height")
                        val height: Int?,
                        @SerializedName("ratio")
                        val ratio: String?,
                        @SerializedName("url")
                        val url: String?,
                        @SerializedName("width")
                        val width: Int?
                    )

                    data class Links(
                        @SerializedName("self")
                        val self: Self?
                    ) {
                        data class Self(
                            @SerializedName("href")
                            val href: String?
                        )
                    }

                    data class Location(
                        @SerializedName("latitude")
                        val latitude: String?,
                        @SerializedName("longitude")
                        val longitude: String?
                    )

                    data class Market(
                        @SerializedName("id")
                        val id: String?,
                        @SerializedName("name")
                        val name: String?
                    )

                    data class State(
                        @SerializedName("name")
                        val name: String?,
                        @SerializedName("stateCode")
                        val stateCode: String?
                    )

                    data class UpcomingEvents(
                        @SerializedName("_total")
                        val total: Int?,
                        @SerializedName("universe")
                        val universe: Int?
                    )
                }
            }

            data class Image(
                @SerializedName("fallback")
                val fallback: Boolean?,
                @SerializedName("height")
                val height: Int?,
                @SerializedName("ratio")
                val ratio: String?,
                @SerializedName("url")
                val url: String?,
                @SerializedName("width")
                val width: Int?
            )

            data class Links(
                @SerializedName("self")
                val self: Self?,
                @SerializedName("venues")
                val venues: List<Venue?>?
            ) {
                data class Self(
                    @SerializedName("href")
                    val href: String?
                )

                data class Venue(
                    @SerializedName("href")
                    val href: String?
                )
            }

            data class Sales(
                @SerializedName("public")
                val `public`: Public?
            ) {
                data class Public(
                    @SerializedName("endDateTime")
                    val endDateTime: String?,
                    @SerializedName("startDateTime")
                    val startDateTime: String?,
                    @SerializedName("startTBA")
                    val startTBA: Boolean?,
                    @SerializedName("startTBD")
                    val startTBD: Boolean?
                )
            }
        }
    }

    data class Links(
        @SerializedName("self")
        val self: Self?
    ) {
        data class Self(
            @SerializedName("href")
            val href: String?
        )
    }

    data class Page(
        @SerializedName("number")
        val number: Int?,
        @SerializedName("size")
        val size: Int?,
        @SerializedName("totalElements")
        val totalElements: Int?,
        @SerializedName("totalPages")
        val totalPages: Int?
    )
}
