package com.example.ecoshop.customViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.Product
import com.example.ecoshop.databinding.ItemTopProductsBinding
import com.example.ecoshop.utils.ListDiffCallBack
import com.example.ecoshop.utils.ListItemClickListener

class ProductBannerAdapter(private val clickListener: ListItemClickListener<Product>,
                           compareItems: (old: Product, new: Product) -> Boolean,
                           compareContents: (old: Product, new: Product) -> Boolean):
    ListAdapter<Product, ProductBannerAdapter.TopProductsHolder>
        (ListDiffCallBack(compareItems, compareContents)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductsHolder {
        return TopProductsHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TopProductsHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, clickListener)
    }

    class TopProductsHolder private constructor(val binding: ItemTopProductsBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product, itemClickListener: ListItemClickListener<Product>){
            binding.product = product
            binding.clickListener = itemClickListener
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): TopProductsHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTopProductsBinding.inflate(layoutInflater, parent, false)
                return TopProductsHolder(binding)
            }
        }
    }
}