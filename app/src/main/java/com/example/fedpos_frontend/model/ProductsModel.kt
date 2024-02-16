package com.example.fedpos_frontend.model

import okhttp3.MultipartBody
import retrofit2.http.Field

data class ProductsModel(
    val name: String,
    val description: String,
    val targetAmount: Int,
    val unit: String,
    val type: String,
    val vat: Int,
    val discount: Int,
    val image: MultipartBody.Part




)

//    @Field("name") name: String,
//    @Field("description") description: String,
//    @Field("targetAmount") targetAmount: Int,
//    @Field("unit") unit: String,
//    @Field("type") type: String,
//    // @Part image: MultipartBody.Part

