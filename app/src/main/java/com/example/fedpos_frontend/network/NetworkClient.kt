package com.example.fedpos_frontend.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object NetworkClient {
    private var retrofit: Retrofit? = null
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl("https://febpos.coptic.co.ke/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}
