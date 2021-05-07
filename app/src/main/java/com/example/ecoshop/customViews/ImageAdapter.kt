package com.example.ecoshop.customViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.model.Image
import com.example.ecoshop.databinding.ImageItemBinding
import com.example.ecoshop.utils.ListDiffCallBack
import com.example.ecoshop.utils.ListItemClickListener

class ImageAdapter(private val clickListener: ListItemClickListener<Image>,
                   compareItems: (old: Image, new: Image) -> Boolean,
                   compareContents: (old: Image, new: Image) -> Boolean):
        ListAdapter<Image, ImageAdapter.ImageHolder>(ListDiffCallBack(compareItems, compareContents)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val image = getItem(position)
        holder.bind(clickListener, image)
    }

    class ImageHolder private constructor(private val binding: ImageItemBinding):
            RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: ListItemClickListener<Image>, image: Image){
            binding.image = image
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ImageHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ImageItemBinding.inflate(layoutInflater, parent, false)
                return ImageHolder(binding)
            }
        }
    }
}