package com.example.ecoshop.categories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecoshop.Product
import com.example.ecoshop.home.ApiStatus
import com.example.ecoshop.model.ProductCategory
import com.example.ecoshop.network.ProductRepository
import com.example.ecoshop.network.ProductRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class CategoriesViewModel: ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repository: ProductRepository = ProductRepositoryImpl()
    private val _categories = MutableLiveData<List<ProductCategory>>()
    val categories: LiveData<List<ProductCategory>>
        get() = _categories
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _navigateToSelectedProperty = MutableLiveData<ProductCategory>()
    val navigateToSelectedProperty: LiveData<ProductCategory>
        get() = _navigateToSelectedProperty

    init {
        getCategories()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getCategories(){
        coroutineScope.launch {
            val getPropertyDeferred = repository.getProducts().getCategoriesAsync()
            try {
                _status.value = ApiStatus.LOADING
                val listResult = getPropertyDeferred.await()
                _categories.value = listResult
                _status.value = ApiStatus.DONE
            }catch (e: Exception){
                Log.d("CategoriesViewModel", e.message.toString())
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun displayPropertyDetails(productCategory: ProductCategory) {
        _navigateToSelectedProperty.value = productCategory
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}