package com.example.ecoshop.customViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.model.Product
import com.example.ecoshop.databinding.ListItemVerticalBinding
import com.example.ecoshop.utils.ListDiffCallBack
import com.example.ecoshop.utils.ListItemClickListener

class ListProductVerticalAdapter(private val clickListener: ListItemClickListener<Product>,
                                 compareItems: (old: Product, new: Product) -> Boolean,
                                 compareContents: (old: Product, new: Product) -> Boolean):
        ListAdapter<Product, ListProductVerticalAdapter.ListVerticalHolder>
        (ListDiffCallBack(compareItems, compareContents)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVerticalHolder {
        return ListVerticalHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListVerticalHolder, position: Int) {
        val product = getItem(position)
        holder.bind(clickListener, product)
    }

    class ListVerticalHolder private constructor(private val binding: ListItemVerticalBinding):
            RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: ListItemClickListener<Product>, product: Product){
            binding.product = product
            binding.onClickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ListVerticalHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemVerticalBinding.inflate(layoutInflater, parent, false)
                return ListVerticalHolder(binding)
            }
        }
    }
}