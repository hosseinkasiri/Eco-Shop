package com.example.ecoshop

import com.squareup.moshi.Json
import java.net.URL

data class Product(val id: Int,
                   val name: String,
                   val status: String,
)