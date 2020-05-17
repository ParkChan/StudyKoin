package com.examsample.ui.bookmark.adapter

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.examsample.ui.bookmark.local.BookmarkDatabase
import com.examsample.ui.bookmark.model.BookmarkModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@BindingAdapter("bookMarkListData")
fun setBookMarkListData(recyclerView: RecyclerView, items: List<BookmarkModel>?) {
    items?.let {
        if (recyclerView.adapter is BookmarkAdapter) {
            (recyclerView.adapter as BookmarkAdapter).submitList(items)
        }
    }
}

@BindingAdapter("deleteBookmark")
fun deleteBookmark(view: View, model: BookmarkModel?) {
    view.setOnClickListener {
        model?.let {
            Toast.makeText(view.context, "${model.name} delete bookmark!!", Toast.LENGTH_SHORT)
                .show()
            CoroutineScope(Dispatchers.IO).launch {
                BookmarkDatabase.getInstance(view.context).bookmarkDao().delete(model)
            }
        }
    }
}