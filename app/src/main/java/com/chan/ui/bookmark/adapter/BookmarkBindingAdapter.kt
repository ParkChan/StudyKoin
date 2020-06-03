package com.chan.ui.bookmark.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chan.R
import com.chan.ui.bookmark.model.BookmarkModel
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("bookMarkListData")
fun setBookMarkListData(recyclerView: RecyclerView, items: List<BookmarkModel>?) {
    items?.let {
        if (recyclerView.adapter is BookmarkAdapter) {
            (recyclerView.adapter as BookmarkAdapter).submitList(items)
        }
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("dateTime")
fun setDateTime(textView: TextView, timestamp: Long) {
    val date = Date(timestamp)
    val simpleDateFormat = SimpleDateFormat(
        textView.context.getString(R.string.bookmark_register_yyyy_mm_dd_ss)
    )
    textView.text = simpleDateFormat.format(date)
}