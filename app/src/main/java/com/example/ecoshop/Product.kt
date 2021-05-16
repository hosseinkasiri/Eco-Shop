package com.example.ecoshop

import android.os.Parcel
import android.os.Parcelable
import com.example.ecoshop.model.Image
import com.example.ecoshop.model.ProductCategory
import com.example.ecoshop.model.Tag
import com.squareup.moshi.Json

data class Product(val id: Int,
                   val name: String?,
                   val status: String?,
                   val type: String?,
                   val description: String?,
                   @Json(name = "short_description")
                   val shortDescription: String?,
                   @Json(name = "sale_price")
                   val salePrice: String?,
                   @Json(name = "regular_price")
                   val regularPrice: String?,
                   val price: String?,
                   val weight: String?,
                   @Json(name = "average_rating")
                   val averageRating: String?,
                   @Json(name = "rating_count")
                   val ratingCount: Int,
                   @Json(name = "on_sale")
                   val onSale: Boolean,
                   val images: List<Image>?,
                   @Json(name = "related_ids")
                   val relatedProductIds: List<Int>?,
                   val tags: List<Tag>?,
                   val categories: List<ProductCategory>?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.createTypedArrayList(Image),
        TODO("relatedProductIds"),
        parcel.createTypedArrayList(Tag),
        parcel.createTypedArrayList(ProductCategory)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeString(type)
        parcel.writeString(description)
        parcel.writeString(shortDescription)
        parcel.writeString(salePrice)
        parcel.writeString(regularPrice)
        parcel.writeString(price)
        parcel.writeString(weight)
        parcel.writeString(averageRating)
        parcel.writeInt(ratingCount)
        parcel.writeByte(if (onSale) 1 else 0)
        parcel.writeTypedList(images)
        parcel.writeTypedList(tags)
        parcel.writeTypedList(categories)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}