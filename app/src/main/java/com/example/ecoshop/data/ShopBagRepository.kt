package com.example.ecoshop.data

import android.content.Context
import android.content.SharedPreferences
import com.example.ecoshop.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface ShopBagRepository {
    fun add(product: Product)
    fun delete(product: Product)
    fun removeAll()
    fun getProductList() : ArrayList<Product>
}

class LocalShopBagRepository: ShopBagRepository {

    private val KEY = "saveObject"
    private val PREF_NAME = "sharePreferences"
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    constructor(context: Context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    override fun add(product: Product) {
       var list = getProductList()
        list.add(product)
        val gson = Gson()
        val json: String = gson.toJson(list)
        set(json)
    }

    override fun delete(product: Product) {
        var list = getProductList()
        list.remove(product)
        val gson = Gson()
        val json: String = gson.toJson(list)
        set(json)
    }

    override fun removeAll() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()
    }

    override fun getProductList(): ArrayList<Product> {
        var arrayItems = ArrayList<Product>()
        val serializedObject = sharedPreferences.getString(KEY, null)
        if (serializedObject != null) {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Product?>?>() {}.type
            arrayItems = gson.fromJson(serializedObject, type)
        }
        return arrayItems
    }

    fun set(value: String?) {
        editor.putString(KEY, value)
        editor.commit()
    }
}