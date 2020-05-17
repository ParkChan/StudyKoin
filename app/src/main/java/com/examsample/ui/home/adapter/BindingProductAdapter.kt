package com.examsample.ui.home.adapter

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.examsample.ui.bookmark.local.BookmarkDatabase
import com.examsample.ui.bookmark.model.BookmarkModel
import com.examsample.ui.home.model.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@BindingAdapter("productListData")
fun setProductListData(recyclerView: RecyclerView, items: List<ProductModel>?) {
    items?.let{
        if (recyclerView.adapter is ProductAdapter) {
            (recyclerView.adapter as ProductAdapter).submitList(it)
        }
    }
}


@BindingAdapter("addBookMark")
fun addBookMark(view: View, model: ProductModel) {
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
    view.setOnClickListener {
        Toast.makeText(view.context, "${model.name} add Bookmark!!", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            view.context?.let { BookmarkDatabase.getInstance(it).bookmarkDao().insert(bookmarkModel) }
        }
    }
}