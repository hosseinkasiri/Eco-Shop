package com.example.ecoshop

import com.squareup.moshi.Json
import java.net.URL

data class Product(val id: Int,
                   val name: String,
                   val status: String,
                   val type: String,
                   val description: String,
                   @Json(name = "short_description")
                   val shortDescription: String,
                   val price: String
)