package com.example.ecoshop.customViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.model.Product
import com.example.ecoshop.databinding.ItemShoppingBinding
import com.example.ecoshop.model.ShoppingItem
import com.example.ecoshop.utils.ListDiffCallBack
import com.example.ecoshop.utils.ListItemClickListener

class ShoppingAdapter(private val clickListener: ListItemClickListener<Product>,
                      compareItems: (old: ShoppingItem, new: ShoppingItem) -> Boolean,
                      compareContents: (old: ShoppingItem, new: ShoppingItem) -> Boolean):
    ListAdapter<ShoppingItem, ShoppingAdapter.ShoppingHolder>(ListDiffCallBack(compareItems, compareContents)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingHolder {
        return ShoppingHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShoppingHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, clickListener)
    }

    class ShoppingHolder private constructor(val binding: ItemShoppingBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shoppingItem: ShoppingItem, clickListener: ListItemClickListener<Product>){
            binding.shoppingItem = shoppingItem
            binding.clickListener = clickListener
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