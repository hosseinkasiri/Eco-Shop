package com.example.ecoshop.utils

import androidx.recyclerview.widget.DiffUtil

class ListDiffCallBack<T>(
        private val compareItems: (old: T, new: T) -> Boolean,
        private val compareContents: (old: T, new: T) -> Boolean):
        DiffUtil.ItemCallback<T>(){
    override fun areItemsTheSame(old: T, new: T) = compareItems(old, new)
    override fun areContentsTheSame(old: T, new: T) = compareContents(old, new)
}