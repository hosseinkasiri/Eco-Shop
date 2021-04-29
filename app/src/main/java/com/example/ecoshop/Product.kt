package com.example.ecoshop

import com.squareup.moshi.Json
import java.io.Serializable

data class Product(val id: Int,
                   val name: String,
                   val status: String,
                   val type: String,
                   val description: String,
                   @Json(name = "short_description")
                   val shortDescription: String,
                   @Json(name = "sale_price")
                   val salePrice: String,
                   @Json(name = "regular_price")
                   val regularPrice: String,
                   val price: String,
                   val weight: String,
                   @Json(name = "average_rating")
                   val averageRating: String,
                   @Json(name = "rating_count")
                   val ratingCount: Int,
                   val images: List<Image>): Serializable