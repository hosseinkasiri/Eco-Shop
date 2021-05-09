package com.example.ecoshop.detailCategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecoshop.model.ProductCategory

class DetailCategoryViewModel(productCategory: ProductCategory, application: Application):
    AndroidViewModel(application) {

    private val _productCategory = MutableLiveData<ProductCategory>()
    val productCategory: LiveData<ProductCategory>
        get() = _productCategory

    init {
        _productCategory.value = productCategory
    }
}