package com.example.ecoshop

import android.os.Parcel
import android.os.Parcelable
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
                   val images: List<Image>?): Parcelable {
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
            parcel.createTypedArrayList(Image)) {
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
        parcel.writeTypedList(images)
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