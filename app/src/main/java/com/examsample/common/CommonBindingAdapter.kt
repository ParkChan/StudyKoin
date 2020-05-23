package com.examsample.common

import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.examsample.ui.detail.ProductDetailViewModel
import com.examsample.ui.home.model.ProductModel
import com.examsample.ui.home.viewmodel.HomeViewModel
import java.text.DecimalFormat

@BindingAdapter("bindImage")
fun setBindImage(
    view: ImageView,
    imgUrl: String
) {
    Glide.with(view.context)
        .load(imgUrl)
        .thumbnail(1.0f)
        .into(view)
}

@BindingAdapter("textWon")
fun setTextWon(
    textView: TextView,
    price: Int
) {
    val formatter = DecimalFormat("###,###.##원")
    textView.text = formatter.format(price)
}

@BindingAdapter("isBookmark", "productModel")
fun setBookmark(toggleButton: ToggleButton, viewModel: ViewModel, productModel: ProductModel) {
    if (viewModel is HomeViewModel) {
        viewModel.isBookMark(toggleButton.context, productModel, onResult = {
            toggleButton.isChecked = it
        })
    } else if (viewModel is ProductDetailViewModel) {
        viewModel.isBookMark(toggleButton.context, productModel, onResult = {
            toggleButton.isChecked = it
        })
    }
}