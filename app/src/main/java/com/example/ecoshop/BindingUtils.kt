package com.example.ecoshop

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecoshop.home.ApiStatus
import com.example.ecoshop.home.ListItemClickListener
import com.example.ecoshop.home.ListProductAdapter
import com.example.ecoshop.home.ListProductVerticalAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Product>?){
    val adapter = recyclerView.adapter as ListProductAdapter
    adapter.submitList(data)
    recyclerView.adapter = adapter
}

@BindingAdapter("verticalListData")
fun bindVerticalRecycler(recyclerView: RecyclerView, data: List<Product>?){
    val adapter = recyclerView.adapter as ListProductVerticalAdapter
    adapter.submitList(data)
    recyclerView.adapter = adapter
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?){
    Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
}

@BindingAdapter("setTextTextView")
fun setTextTextView(textView: TextView, string: String?){
    textView.text = string
}

@BindingAdapter("priceProduct")
fun bindPrice(textView: TextView, salePrice: String?){
    textView.text = salePrice + "تومان"
}

@BindingAdapter("ApiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("visibilityTextView")
fun visibilityTextView(textView: TextView, status: ApiStatus?) {
    if (status == ApiStatus.DONE)
        textView.visibility = View.VISIBLE
    else
        textView.visibility = View.GONE
}