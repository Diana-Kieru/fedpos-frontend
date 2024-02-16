package com.example.fedpos_frontend.network

import com.example.fedpos_frontend.model.AddProductResponse
import com.example.fedpos_frontend.model.ProductsModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {


    @POST("products")
    fun addProduct(
     @Body ProductsModel: ProductsModel

    ): Call<AddProductResponse>

    @GET("products")
    fun getProducts(): Call<List<AddProductResponse>>

    @POST("sales")
    fun addSale()

}