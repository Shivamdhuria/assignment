package com.example.assignment.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    val title:String,
    val author: String,
    val description: String,
    val url: String,
    val content: String,
    val urlToImage: String?
): Parcelable