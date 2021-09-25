package com.example.assignment.ui.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

internal object ImageLoader {

    fun loadImage(context: Context, url: String, imageView: ImageView, radius: Int = 1) {
        Glide.with(context)
            .load(url)
            .fitCenter()
            .transition(withCrossFade())
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(radius)))
            .into(imageView)
    }

    fun loadImage(context: Context, resId: Int, imageView: ImageView, radius: Int = 1) {
        Glide.with(context)
            .load(resId)
            .transition(withCrossFade())
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(radius)))
            .into(imageView)
    }
}