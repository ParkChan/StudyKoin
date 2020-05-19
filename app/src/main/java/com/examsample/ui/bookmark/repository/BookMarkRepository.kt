package com.examsample.ui.bookmark.repository

import android.content.Context
import com.examsample.ui.bookmark.BookMarkSortType
import com.examsample.ui.bookmark.local.BookmarkDatabase
import com.examsample.ui.bookmark.model.BookmarkModel
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookMarkRepository {

    fun selectAll(
        context: Context,
        sort: BookMarkSortType,
        onSuccess: (list: List<BookmarkModel>) -> Unit,
        onFail: (error: String) -> Unit
    ): Disposable = when(sort){
        BookMarkSortType.RegDateDesc -> {
            BookmarkDatabase.getInstance(context).bookmarkDao().selectAllRegDateDesc()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    onSuccess(it)
                }) {
                    onFail(it.toString())
                }
        }
        BookMarkSortType.RegDateAsc -> {
            BookmarkDatabase.getInstance(context).bookmarkDao().selectAllRegDateAsc()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    onSuccess(it)
                }) {
                    onFail(it.toString())
                }
        }BookMarkSortType.ReviewRatingDesc -> {
            BookmarkDatabase.getInstance(context).bookmarkDao().selectAllReviewDesc()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    onSuccess(it)
                }) {
                    onFail(it.toString())
                }
        }BookMarkSortType.ReviewRatingAsc -> {
            BookmarkDatabase.getInstance(context).bookmarkDao().selectAllReviewAsc()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    onSuccess(it)
                }) {
                    onFail(it.toString())
                }
        }
    }


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