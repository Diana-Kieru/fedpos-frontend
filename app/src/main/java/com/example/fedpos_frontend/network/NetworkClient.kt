package com.example.fedpos_frontend.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object NetworkClient {
    private var retrofit: Retrofit? = null

    val okHttpClient = OkHttpClient.Builder()
        .apply {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logging)

        }.build()

    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .client(okHttpClient)

                    .baseUrl("https://febpos.coptic.co.ke/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}
