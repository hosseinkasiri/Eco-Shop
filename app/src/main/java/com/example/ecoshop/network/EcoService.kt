package com.example.ecoshop.network

import com.example.ecoshop.Product
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_API = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/"
private const val CONSUMER_KEY = "ck_fcaf3ae3cc25408cfacf3a4706ce12017705ba81"
private const val CONSUMER_SECRET = "cs_aebe5b20c7df9f1d35eb9684bf5726b7a44c2f95"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val client = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor{ chain ->
    val url = chain
            .request()
            .url
            .newBuilder()
            .addQueryParameter("consumer_key", CONSUMER_KEY)
            .addQueryParameter("consumer_secret", CONSUMER_SECRET)
            .build()
    chain.proceed(chain.request().newBuilder().url(url).build())
}.build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_API)
        .client(client)
        .build()

interface EcoService {
    @GET("products")
    fun getPropertiesAsync(): Deferred<List<Product>>
}

object EcoApi {
    val retrofitService: EcoService by lazy {
        retrofit.create(EcoService::class.java)
    }
}