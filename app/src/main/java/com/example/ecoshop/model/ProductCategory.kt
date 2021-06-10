package com.example.ecoshop.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductCategory(val id: Int,
                           val name: String?,
                           val description: String?,
                           val image: Image?): Parcelable