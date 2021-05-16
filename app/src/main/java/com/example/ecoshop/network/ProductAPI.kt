package com.example.ecoshop.network

import com.example.ecoshop.Product
import com.example.ecoshop.model.ProductCategory
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductAPI {
    @GET("products")
    fun getPropertiesAsync(@Query("orderby") value: String):
            Deferred<List<Product>>

    @GET("products/categories")
    fun getCategoriesAsync():
            Deferred<List<ProductCategory>>

    @GET("products")
    fun getProductsCategory(@Query("category") id: Int):
            Deferred<List<Product>>

    @GET("products/{id}")
    fun getProductByIdAsync(@Path("id") id: Int):
            Deferred<Product>
}