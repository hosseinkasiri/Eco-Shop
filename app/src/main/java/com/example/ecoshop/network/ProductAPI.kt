package com.example.ecoshop.network

import com.example.ecoshop.Product
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductAPI {
    @GET("products")
    fun getPropertiesAsync(@Query("orderby") value: String):
            Deferred<List<Product>>
}