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

class HomeViewModel: ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repository: ProductRepository = ProductRepositoryImpl()
    private val _property = MutableLiveData<List<Product>>()
    val property: LiveData<List<Product>>
        get() = _property

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
                val listResult = getProperties.await()
                _property.value = listResult
            }catch (e:Exception){
                Log.d("ListProductViewModel", e.message + "")
                _property.value = ArrayList()
            }
        }
    }
}