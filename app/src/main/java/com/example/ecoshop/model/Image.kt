package com.example.ecoshop.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(val id: Int,
                 val src: String?,
                 val name: String?): Parcelable