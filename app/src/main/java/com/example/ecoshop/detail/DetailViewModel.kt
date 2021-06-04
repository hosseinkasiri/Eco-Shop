package com.example.ecoshop.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecoshop.Product
import com.example.ecoshop.home.ApiStatus
import com.example.ecoshop.data.LocalShopBagRepository
import com.example.ecoshop.data.ShopBagRepository
import com.example.ecoshop.network.ProductRepository
import com.example.ecoshop.network.ProductRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(product: Product, application: Application): AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repository: ProductRepository = ProductRepositoryImpl()
    private val shopBagRepository: ShopBagRepository
    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product>
        get() = _selectedProduct
    private val _similarProducts = MutableLiveData<List<Product>>()
    val similarProducts: LiveData<List<Product>>
        get() = _similarProducts
    private val _statusDetail = MutableLiveData<ApiStatus>()
    val statusDetail: LiveData<ApiStatus>
        get() = _statusDetail

    init {
        shopBagRepository = LocalShopBagRepository(application)
        _selectedProduct.value = product
        getSimilarProducts()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getSimilarProducts(){
        coroutineScope.launch {
            val listResult = ArrayList<Product>()
            val size = _selectedProduct.value?.relatedProductIds?.size?.minus(1) as Int
            try {
                _statusDetail.value = ApiStatus.LOADING
                for (i in 0 until size){
                    val getProperty = _selectedProduct.value!!.relatedProductIds?.get(i)?.let {
                        repository.getProducts().getProductByIdAsync(it)
                    }
                    listResult.add(getProperty!!.await())
                }
                _statusDetail.value = ApiStatus.DONE
                _similarProducts.value = listResult
            }catch (e: Exception){
                Log.d("detailViewModel", e.message.toString())
                _statusDetail.value = ApiStatus.ERROR
            }
        }
    }

    fun shopProduct() {
        _selectedProduct.value?.let { shopBagRepository.add(it) }
    }

    fun reLoadingDetail(){
        getSimilarProducts()
    }
}