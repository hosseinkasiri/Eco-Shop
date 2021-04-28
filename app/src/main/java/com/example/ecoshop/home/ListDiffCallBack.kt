package com.example.ecoshop.home

import androidx.recyclerview.widget.DiffUtil
import com.example.ecoshop.Product

class ListDiffCallBack: DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}