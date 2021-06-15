package com.example.ecoshop.search

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecoshop.home.ApiStatus
import com.example.ecoshop.model.Product
import com.example.ecoshop.network.ProductRepository
import com.example.ecoshop.network.ProductRepositoryImpl
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.collections.ArrayList

class SearchViewModel: ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repository: ProductRepository = ProductRepositoryImpl()

    private val _property = MutableLiveData<List<Product>>()
    val property: LiveData<List<Product>> get() = _property
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status
    private val timer: CountDownTimer
    private var searchingString: String

    init {
        searchingString = ""
        timer = object : CountDownTimer(1000, 2000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                getProducts(searchingString)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun startTimerProducts(string: String){
        timer.start()
        searchingString = string
    }

    private fun getProducts(search: String){
        coroutineScope.launch {
            val getProductDeferred = repository.getProducts().getSearchAsync(search)
            try {
                _status.value = ApiStatus.LOADING
                val listResult = getProductDeferred.await()
                _property.value = listResult
                _status.value = ApiStatus.DONE
            }catch (e: Exception){
                Log.d("search", e.message.toString())
                _property.value = ArrayList()
                _status.value = ApiStatus.ERROR
            }
        }
    }
}