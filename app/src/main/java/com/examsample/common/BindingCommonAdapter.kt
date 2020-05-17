package com.examsample.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("visibility")
fun setVisibility(view: View, text: String?) {
    if (text.isNullOrEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("bindImage")
fun setProductImage(
    view: ImageView,
    imgUrl: String
) {
    Glide.with(view.context)
        .load(imgUrl)
        .thumbnail(1.0f)
        .into(view)
}
