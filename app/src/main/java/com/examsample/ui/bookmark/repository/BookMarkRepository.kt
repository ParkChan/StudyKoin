package com.examsample.ui.bookmark.repository

import android.content.Context
import com.examsample.ui.bookmark.local.BookmarkDatabase
import com.examsample.ui.bookmark.model.BookmarkModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookMarkRepository {

    fun deleteBookMark(context: Context, model: BookmarkModel) {
        CoroutineScope(Dispatchers.IO).launch {
            BookmarkDatabase.getInstance(context).bookmarkDao().delete(model)
        }
    }

    fun insertBookMark(context: Context, model: BookmarkModel) {
        CoroutineScope(Dispatchers.IO).launch {
            BookmarkDatabase.getInstance(context).bookmarkDao().insert(model)
        }
    }

}