package com.example.ecoshop.shoppingCart

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecoshop.Product
import com.example.ecoshop.data.LocalShopBagRepository
import com.example.ecoshop.data.ShopBagRepository
import com.example.ecoshop.model.ShoppingItem

class ShoppingCartViewModel (application: Application): ViewModel() {

    private val shopBagRepository: ShopBagRepository
    private val shoppedProducts = MutableLiveData<List<Product>>()
    private val _totalDiscounts = MutableLiveData<String>()
    val totalDiscounts : LiveData<String>
        get() = _totalDiscounts
    private val _totalPrises = MutableLiveData<String>()
    val totalPrises : LiveData<String>
        get() = _totalPrises
    private val _totalShopped = MutableLiveData<String>()
    val totalShopped : LiveData<String>
        get() = _totalShopped
    private val _shoppingItems = MutableLiveData<List<ShoppingItem>>()
    val shoppingItems : LiveData<List<ShoppingItem>>
        get() = _shoppingItems

    init {
        shopBagRepository = LocalShopBagRepository(application)
        getShopped()
        getDiscounts()
        getPrises()
        getTotalShopping()
        fillShoppingItem()
    }

    fun getShopped(){
        shoppedProducts.value = shopBagRepository.getProductList()
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
        for (element in shoppedProducts.value!!){
            if (element.onSale){
                discounts += (Integer.parseInt(element.regularPrice) - Integer.parseInt(element.price))
            }
        }
        _totalDiscounts.value = discounts.toString()
    }

    fun getPrises(){
        var prises = 0
        for (element in shoppedProducts.value!!){
            prises += Integer.parseInt(element.regularPrice)
        }
        _totalPrises.value = prises.toString()
    }

    fun fillShoppingItem(){
        val hMap = shoppedProducts.value?.groupingBy { it.id }?.eachCount()
        _shoppingItems.value = hMap?.entries?.
        map{ ShoppingItem(shoppedProducts.value!!.last {product -> product.id == it.key}, it.value) } ?: ArrayList()
    }
}