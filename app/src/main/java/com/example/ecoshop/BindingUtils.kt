package com.example.ecoshop

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecoshop.customViews.CategoryAdapter
import com.example.ecoshop.customViews.ImageAdapter
import com.example.ecoshop.customViews.ListProductAdapter
import com.example.ecoshop.customViews.ListProductVerticalAdapter
import com.example.ecoshop.home.ApiStatus
import com.example.ecoshop.model.Image
import com.example.ecoshop.model.ProductCategory
import com.example.ecoshop.model.Tag

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

@BindingAdapter("imageListData")
fun bindImageRecycler(recyclerView: RecyclerView, images: List<Image>?){
    val adapter = recyclerView.adapter as ImageAdapter
    adapter.submitList(images)
    recyclerView.adapter = adapter
}

@BindingAdapter("categoriesListData")
fun bindCategoryRecycler(recyclerView: RecyclerView, categories: List<ProductCategory>?){
    val adapter = recyclerView.adapter as CategoryAdapter
    adapter.submitList(categories)
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
fun bindPrice(textView: TextView, price: String?){
    var array = ArrayList<Char>()
    val charArray = price!!.toCharArray()
    var number = 1
    for (i in charArray.size-1 downTo 0){
        if (number%3 == 0 && number != 1){
            array.add(charArray[i])
            array.add(',')
            number++
            continue
        }
        array.add(charArray[i])
        number++
    }
    array.reverse()
    textView.text = array.joinToString("") + " " + "تومان"
}

@BindingAdapter("setCategoryProduct")
fun bindCategoryProduct(textView: TextView, categories: List<ProductCategory>){
    var category = ""
    for (i in categories.indices){
        category += if (i != categories.size -1)
            categories[i].name + " | "
        else
            categories[i].name

    }
    textView.text = category
}

@BindingAdapter("setTags")
fun bindProductTags(textView: TextView, tags: List<Tag>){
    var tag = ""
    for (i in tags.indices){
        tag += if (i != tags.size -1)
            tags[i].name + " | "
        else
            tags[i].name

    }
    textView.text = tag
}

@BindingAdapter("setRatingCount")
fun bindRatingCount(textView: TextView, string: String?){
    textView.text = string + " نظر"
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

@RequiresApi(Build.VERSION_CODES.N)
@BindingAdapter("setHtmlText")
fun setHtmlText(textView: TextView, string: String?){
    textView.text = Html.fromHtml(string, Html.FROM_HTML_MODE_COMPACT)
}

@BindingAdapter("averageRating", "starNumber")
fun bindStarRating(imageView: ImageView, averageRating: Double, starNumber: Int){
    when(averageRating - starNumber){
        in 0.00..5.00 -> imageView.setImageResource(R.drawable.ic_action_full_star)
        in -0.99..-0.01 -> imageView.setImageResource(R.drawable.ic_action_half_star)
        else -> imageView.setImageResource(R.drawable.ic_action_empty_star)
    }
}

@BindingAdapter("regularPriceVisibility")
fun bindRegularPrice(relativeLayout: RelativeLayout, onSale: Boolean){
    if (!onSale) relativeLayout.visibility = View.GONE
}
