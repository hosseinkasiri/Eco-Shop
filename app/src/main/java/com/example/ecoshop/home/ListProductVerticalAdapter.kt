package com.example.ecoshop.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.Product
import com.example.ecoshop.databinding.ListItemVerticalBinding

class ListProductVerticalAdapter(val clickListener: ListItemClickListener):
        ListAdapter<Product, ListProductVerticalAdapter.ListVerticalHolder>(ListDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVerticalHolder {
        return ListVerticalHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListVerticalHolder, position: Int) {
        val product = getItem(position)
        holder.bind(clickListener, product)
    }

    class ListVerticalHolder private constructor(val binding: ListItemVerticalBinding):
            RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: ListItemClickListener, product: Product){
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