package com.example.ecoshop.customViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.databinding.ItemSelectedBinding
import com.example.ecoshop.utils.ListItemClickListener

class CircularIndicatorAdapter(private val clickListener: ListItemClickListener<Unit>, private val size: Int):
    RecyclerView.Adapter<CircularIndicatorAdapter.SelectedHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedHolder {
        return SelectedHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SelectedHolder, position: Int) {
        holder.bind(clickListener)
    }

    override fun getItemCount(): Int {
        return size
    }

    class SelectedHolder (val binding: ItemSelectedBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: ListItemClickListener<Unit>){
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }


        companion object {
            fun from(parent: ViewGroup): SelectedHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSelectedBinding.inflate(layoutInflater, parent, false)
                return SelectedHolder(binding)
            }
        }
    }
}