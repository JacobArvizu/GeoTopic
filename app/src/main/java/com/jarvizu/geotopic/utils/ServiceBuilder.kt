package com.jarvizu.geotopic.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber


object ServiceBuilder {

    private val httpLoggingInterceptor = HttpLoggingInterceptor { message: String? ->
        Timber.d(
            "HTTP::$message"
        )
    }

    init {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    var okHttpClient: OkHttpClient = OkHttpClient()
        .newBuilder() //httpLogging interceptor for logging network requests
        .addInterceptor(httpLoggingInterceptor) //Encryption interceptor for encryption of request data
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}