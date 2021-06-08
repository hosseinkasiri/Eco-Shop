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
    private val _totalDiscounts = MutableLiveData<String>()
    val totalDiscounts : LiveData<String>
        get() = _totalDiscounts
    private val _totalPrises = MutableLiveData<String>()
    val totalPrises : LiveData<String>
        get() = _totalPrises
    private val _totalShopped = MutableLiveData<String>()
    val totalShopped : LiveData<String>
        get() = _totalShopped

    init {
        shopBagRepository = LocalShopBagRepository(application)
        getShopped()
        getDiscounts()
        getPrises()
        getTotalShopping()
    }

    fun getShopped(){
        _shoppedProducts.value = shopBagRepository.getProductList()
    }

    fun deleteProduct(product: Product){
        shopBagRepository.delete(product)
        getShopped()
    }

    fun getTotalShopping(){
        val shopped = Integer.parseInt(_totalPrises.value) - Integer.parseInt(_totalDiscounts.value)
        _totalShopped.value = shopped.toString()
    }

    fun getDiscounts(){
        var discounts = 0
        for (element in _shoppedProducts.value!!){
            if (element.onSale){
                discounts += (Integer.parseInt(element.regularPrice) - Integer.parseInt(element.price))
            }
        }
        _totalDiscounts.value = discounts.toString()
    }

    fun getPrises(){
        var prises = 0
        for (element in _shoppedProducts.value!!){
            prises += Integer.parseInt(element.regularPrice)
        }
        _totalPrises.value = prises.toString()
    }
}