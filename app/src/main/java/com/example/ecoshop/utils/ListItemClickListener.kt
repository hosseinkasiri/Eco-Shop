package com.example.ecoshop.utils

import com.example.ecoshop.Product

class ListItemClickListener(val onClickListener: (product: Product) -> Unit){
    fun onClick(product: Product) = onClickListener(product)
}