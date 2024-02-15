package com.example.fedpos_frontend.network

import com.example.fedpos_frontend.model.AddProductResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("products")
    fun addProduct(
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("targetAmount") targetAmount: Int,
        @Field("unit") unit: String,
        @Field("type") type: String,
        @Part image: MultipartBody.Part
    ): Call<AddProductResponse>

    @POST("sales")
    fun addSale()

}