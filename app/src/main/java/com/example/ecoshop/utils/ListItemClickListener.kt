package com.example.ecoshop.utils

class ListItemClickListener<T>(val onClickListener: (item: T) -> Unit){
    fun onClick(item: T) = onClickListener(item)
}