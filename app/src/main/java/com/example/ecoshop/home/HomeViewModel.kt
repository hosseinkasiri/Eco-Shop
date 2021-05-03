package com.example.ecoshop.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _navigateToSelectedProperty = MutableLiveData<Product>()
    val navigateToSelectedProperty: LiveData<Product>
        get() = _navigateToSelectedProperty

    init {
        getProperties()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getProperties() {
        coroutineScope.launch {
            val getProperties = repository.getProducts().getPropertiesAsync("date")
            try {
                _status.value = ApiStatus.LOADING
                val listResult = getProperties.await()
                _status.value = ApiStatus.DONE
                _property.value = listResult
            }catch (e:Exception){
                Log.d("homeViewModel", e.message + "")
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
}