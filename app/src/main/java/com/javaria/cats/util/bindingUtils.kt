package com.javaria.cats.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}