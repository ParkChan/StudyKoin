package com.examsample.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DecimalFormat


@BindingAdapter("visibility")
fun visibility(view: View, text: String?) {
    if (text.isNullOrEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("bindImage")
fun bindImage(
    view: ImageView,
    imgUrl: String
) {
    Glide.with(view.context)
        .load(imgUrl)
        .thumbnail(1.0f)
        .into(view)
}

@BindingAdapter("textWon")
fun textWon(
    textView: TextView,
    price: Int
) {
    val formatter = DecimalFormat("###,###.##원")
    textView.text = formatter.format(price)

}

