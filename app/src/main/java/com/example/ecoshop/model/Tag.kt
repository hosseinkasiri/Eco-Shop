package com.example.ecoshop.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(val id: Int, val name: String?, val slug: String?): Parcelable
