package com.example.ecoshop.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.Product
import com.example.ecoshop.databinding.ListItemViewBinding

class ListProductAdapter(val clickListener: ListItemClickListener): ListAdapter<Product, ListProductAdapter.ListHolder>(ListDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        return ListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val product = getItem(position)
        holder.bind(clickListener, product)
    }

    class ListHolder private constructor(private val binding: ListItemViewBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: ListItemClickListener, product: Product){
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

    class ListDiffCallBack: DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    class ListItemClickListener(val onClickListener: (product: Product) -> Unit){
        fun onClick(product: Product) = onClickListener(product)
    }
}