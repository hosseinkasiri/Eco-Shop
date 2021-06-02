package com.example.ecoshop.customViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.Product
import com.example.ecoshop.databinding.ItemShoppingBinding
import com.example.ecoshop.utils.ListDiffCallBack
import com.example.ecoshop.utils.ListItemClickListener

class ShoppingAdapter(private val clickListener: ListItemClickListener<Product>,
                      compareItems: (old: Product, new: Product) -> Boolean,
                      compareContents: (old: Product, new: Product) -> Boolean):
    ListAdapter<Product, ShoppingAdapter.ShoppingHolder>(ListDiffCallBack(compareItems, compareContents)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingHolder {
        return ShoppingHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShoppingHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    class ShoppingHolder private constructor(val binding: ItemShoppingBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product){
            binding.product = product
            binding.executePendingBindings()
        }

            companion object {
                fun from(parent: ViewGroup): ShoppingHolder{
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ItemShoppingBinding.inflate(layoutInflater, parent, false)
                    return ShoppingHolder(binding)
                }
            }
    }
}