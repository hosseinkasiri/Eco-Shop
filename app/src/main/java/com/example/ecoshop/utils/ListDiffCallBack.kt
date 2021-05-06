package com.example.ecoshop.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.ecoshop.Product

class ListDiffCallBack<T : Any>(
        private val compareItems: (old: T, new: T) -> Boolean,
        private val compareContents: (old: T, new: T) -> Boolean):
        DiffUtil.ItemCallback<T>(){
    override fun areItemsTheSame(old: T, new: T) = compareItems(old, new)
    override fun areContentsTheSame(old: T, new: T) = compareContents(old, new)
}