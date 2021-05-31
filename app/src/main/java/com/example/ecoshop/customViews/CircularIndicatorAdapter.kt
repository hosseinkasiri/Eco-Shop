package com.example.ecoshop.customViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.databinding.ItemCircularIndicatorBinding

class CircularIndicatorAdapter(private val size: Int):
    RecyclerView.Adapter<CircularIndicatorAdapter.SelectedHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedHolder {
        return SelectedHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SelectedHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return size
    }

    class SelectedHolder (val binding: ItemCircularIndicatorBinding): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): SelectedHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCircularIndicatorBinding.inflate(layoutInflater, parent, false)
                return SelectedHolder(binding)
            }
        }
    }
}