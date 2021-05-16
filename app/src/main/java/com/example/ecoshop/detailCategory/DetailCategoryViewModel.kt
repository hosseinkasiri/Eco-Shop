package com.example.ecoshop.detailCategory

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecoshop.Product
import com.example.ecoshop.model.ProductCategory
import com.example.ecoshop.network.ProductRepository
import com.example.ecoshop.network.ProductRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailCategoryViewModel(productCategory: ProductCategory, application: Application):
        AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repository: ProductRepository = ProductRepositoryImpl()
    private val _productCategory = MutableLiveData<ProductCategory>()
    val productCategory: LiveData<ProductCategory>
        get() = _productCategory
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    init {
        _productCategory.value = productCategory
        getProducts()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getProducts(){
        coroutineScope.launch {
            val getProductDeferred = repository.getProducts().
            getProductsCategory(_productCategory.value!!.id)
            try {
                val listResult = getProductDeferred.await()
                _products.value = listResult
                Log.d("detailCategory", listResult.size.toString())
            }catch (e: Exception){
                Log.d("detailCategory", e.message.toString())
                _products.value = ArrayList()
            }
        }
    }
}