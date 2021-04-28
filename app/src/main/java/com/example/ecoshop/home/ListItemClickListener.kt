package com.example.ecoshop.home

import com.example.ecoshop.Product

class ListItemClickListener(val onClickListener: (product: Product) -> Unit){
    fun onClick(product: Product) = onClickListener(product)
}