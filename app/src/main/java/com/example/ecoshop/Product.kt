package com.example.ecoshop

import android.os.Parcelable
import com.example.ecoshop.model.Image
import com.example.ecoshop.model.ProductCategory
import com.example.ecoshop.model.Tag
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(val id: Int,
                   val name: String?,
                   val status: String?,
                   val type: String?,
                   val description: String?,
                   @Json(name = "short_description")
                   val shortDescription: String?,
                   @Json(name = "sale_price")
                   val salePrice: String?,
                   @Json(name = "regular_price")
                   val regularPrice: String?,
                   val price: String?,
                   val weight: String?,
                   @Json(name = "average_rating")
                   val averageRating: String?,
                   @Json(name = "rating_count")
                   val ratingCount: Int,
                   @Json(name = "on_sale")
                   val onSale: Boolean,
                   val images: List<Image>?,
                   @Json(name = "related_ids")
                   val relatedProductIds: List<Int>?,
                   val tags: List<Tag>?,
                   val categories: List<ProductCategory>?): Parcelable


