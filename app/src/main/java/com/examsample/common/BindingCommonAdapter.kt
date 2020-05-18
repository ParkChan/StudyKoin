package com.examsample.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.examsample.ui.bookmark.model.BookmarkModel
import com.examsample.ui.detail.ProductDetailViewModel
import com.examsample.ui.home.model.ProductModel
import com.examsample.ui.home.viewmodel.HomeViewModel
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

@BindingAdapter("bookmarkListener", "productModel")
fun bookmarkListener(toggleButton: ToggleButton, viewModel: ViewModel, model: ProductModel) {
    val bookmarkModel = BookmarkModel(
        id = model.id,
        name = model.name,
        thumbnail = model.thumbnail,
        imagePath = model.descriptionModel.imagePath,
        subject = model.descriptionModel.subject,
        price = model.descriptionModel.price,
        rate = model.rate,
        regTimeStamp = System.currentTimeMillis()
    )
    toggleButton.setOnClickListener {
        if (toggleButton.isChecked) {
            if (viewModel is HomeViewModel) {
                viewModel.insertBookMark(toggleButton.context, bookmarkModel)
            } else if (viewModel is ProductDetailViewModel) {
                viewModel.insertBookMark(toggleButton.context, bookmarkModel)
            }
        } else {
            if (viewModel is HomeViewModel) {
                viewModel.deleteBookMark(toggleButton.context, bookmarkModel)
            } else if (viewModel is ProductDetailViewModel) {
                viewModel.deleteBookMark(toggleButton.context, bookmarkModel)
            }

        }
    }
}