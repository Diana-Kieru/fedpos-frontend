package com.example.fedpos_frontend.model

data class AddProductResponse(
    val category_id: Int,
    val created_at: String,
    val id: Int,
    val name: String,
    val price: Double,
    val updated_at: String
)