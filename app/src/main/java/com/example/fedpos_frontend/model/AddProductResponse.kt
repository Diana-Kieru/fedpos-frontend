package com.example.fedpos_frontend.model

data class AddProductResponse(
    val category_id: String,
    val created_at: String,
    val description: Any,
    val discount: Any,
    val id: Int,
    val image: Any,
    val name: String,
    val targetAmount: String,
    val updated_at: String,
    val vat: Any
)