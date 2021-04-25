package com.example.ecoshop

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecoshop.home.ListProductAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Product>){
    val adapter = recyclerView.adapter as ListProductAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String){
    Glide.with(imageView.context)
        .load(imageUrl)
        .into(imageView)
}

@BindingAdapter("nameProduct")
fun bindName(textView: TextView, name: String){
    textView.text = name
}

@BindingAdapter("priceProduct")
fun bindPrice(textView: TextView, salePrice: String){
    textView.text = salePrice
}

@BindingAdapter("regularPriceProduct")
fun bindRegularPrice(textView: TextView, regularPrice: String){
    textView.text = regularPrice
}
