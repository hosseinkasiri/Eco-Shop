package com.example.ecoshop.customViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.model.Product
import com.example.ecoshop.databinding.ListItemViewBinding
import com.example.ecoshop.utils.ListDiffCallBack
import com.example.ecoshop.utils.ListItemClickListener

class ListProductAdapter(private val clickListener: ListItemClickListener<Product>,
                         compareItems: (old: Product, new: Product) -> Boolean,
                         compareContents: (old: Product, new: Product) -> Boolean):
        ListAdapter<Product, ListProductAdapter.ListHolder>(ListDiffCallBack(compareItems, compareContents)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        return ListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val product = getItem(position)
        holder.bind(clickListener, product)
    }

    class ListHolder private constructor(private val binding: ListItemViewBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: ListItemClickListener<Product>, product: Product){
            binding.product = product
            binding.onClickListener = clickListener
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): ListHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemViewBinding.inflate(layoutInflater, parent, false)
                return ListHolder(binding)
            }
        }
    }
}