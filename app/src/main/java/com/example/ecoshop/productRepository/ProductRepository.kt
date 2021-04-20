package com.example.ecoshop.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface ProductRepository {
    fun getProducts(): ProductAPI
}

class ProductRepositoryImpl: ProductRepository {
    private  val BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/"
    private  val CONSUMER_KEY = "ck_fcaf3ae3cc25408cfacf3a4706ce12017705ba81"
    private  val CONSUMER_SECRET = "cs_aebe5b20c7df9f1d35eb9684bf5726b7a44c2f95"

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
            .baseUrl(BASE_URL)
            .client(client)
            .build()

    private val retrofitClient: ProductAPI by lazy {
        retrofit.create(ProductAPI::class.java)
    }

    override fun getProducts(): ProductAPI {
        return retrofitClient
    }
}

