package com.example.ecoshop.shoppingCart

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecoshop.Product
import com.example.ecoshop.data.LocalShopBagRepository
import com.example.ecoshop.data.ShopBagRepository

class ShoppingCartViewModel (application: Application): ViewModel() {

    private val shopBagRepository: ShopBagRepository
    private val _shoppedProducts = MutableLiveData<List<Product>>()
    val shoppedProducts : LiveData<List<Product>>
        get() = _shoppedProducts

    init {
        shopBagRepository = LocalShopBagRepository(application)
        getShopped()
    }

    fun getShopped(){
        _shoppedProducts.value = shopBagRepository.getProductList()
    }

    fun deleteProduct(product: Product){
        shopBagRepository.delete(product)
        getShopped()
    }
}