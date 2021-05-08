package com.example.ecoshop.customViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.databinding.ListCategoryItemBinding
import com.example.ecoshop.model.ProductCategory
import com.example.ecoshop.utils.ListDiffCallBack
import com.example.ecoshop.utils.ListItemClickListener

class CategoryAdapter(private val clickListener: ListItemClickListener<ProductCategory>,
                      compareItems: (old: ProductCategory, new: ProductCategory) -> Boolean,
                      compareContents: (old: ProductCategory, new: ProductCategory) -> Boolean):
        ListAdapter<ProductCategory, CategoryAdapter.CategoryHolder>(ListDiffCallBack(compareItems, compareContents)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val productCategory = getItem(position)
        holder.bind(clickListener, productCategory)
    }
    
    class CategoryHolder private constructor(val binding: ListCategoryItemBinding):
            RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: ListItemClickListener<ProductCategory>, productCategory: ProductCategory){
            binding.productCategory = productCategory
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListCategoryItemBinding.inflate(layoutInflater, parent, false)
                return CategoryHolder(binding)
            }
        }
    }
}