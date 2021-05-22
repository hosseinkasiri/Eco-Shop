package com.example.ecoshop.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecoshop.model.Image
import com.example.ecoshop.Product
import com.example.ecoshop.network.ProductRepository
import com.example.ecoshop.network.ProductRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

enum class ApiStatus { LOADING, ERROR, DONE }

class HomeViewModel: ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repository: ProductRepository = ProductRepositoryImpl()
    private val _property = MutableLiveData<List<Product>>()
    val property: LiveData<List<Product>>
        get() = _property
    private val _bestProperty = MutableLiveData<List<Product>>()
    val bestProperty: LiveData<List<Product>>
        get() = _bestProperty
    private val _popularProperty = MutableLiveData<List<Product>>()
    val popularProperty: LiveData<List<Product>>
        get() = _popularProperty
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _navigateToSelectedProperty = MutableLiveData<Product>()
    val navigateToSelectedProperty: LiveData<Product>
        get() = _navigateToSelectedProperty
    private val _topProperties = MutableLiveData<List<Image>>()
    val topProperties: LiveData<List<Image>>
        get() = _topProperties

    init {
        getProperties()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getProperties() {
        coroutineScope.launch {
            val getBestProperties = repository.getProducts().getPropertiesAsync("rating")
            val getProperties = repository.getProducts().getPropertiesAsync("date")
            val getPopularProperties = repository.getProducts().getPropertiesAsync("popularity")
            try {
                _status.value = ApiStatus.LOADING
                val listResult = getProperties.await()
                val bestList = getBestProperties.await()
                val popularList = getPopularProperties.await()
                _property.value = listResult
                _bestProperty.value = bestList
                _popularProperty.value = popularList
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.d("homeViewModel", e.message.toString())
                _status.value = ApiStatus.ERROR
                _property.value = ArrayList()
            }
        }

    }

    fun displayPropertyDetails(product: Product) {
        _navigateToSelectedProperty.value = product
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun getTopProducts(){
        val listProduct = _bestProperty.value
        val listImage = ArrayList<Image>()
        for (i in 0..4){
            listProduct?.get(i)?.images?.get(1)?.let { listImage.add(it) }
        }
        _topProperties.value = listImage
    }
}