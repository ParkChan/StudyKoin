package com.examsample.ui.bookmark.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.examsample.ui.bookmark.model.BookmarkModel


@BindingAdapter("bookMarkListData")
fun setBookMarkListData(recyclerView: RecyclerView, items: List<BookmarkModel>?) {
    items?.let {
        if (recyclerView.adapter is BookmarkAdapter) {
            (recyclerView.adapter as BookmarkAdapter).submitList(items)
        }
    }
}