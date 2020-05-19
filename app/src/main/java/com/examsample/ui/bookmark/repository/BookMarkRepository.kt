package com.examsample.ui.bookmark.repository

import android.content.Context
import com.examsample.ui.bookmark.BookMarkSortType
import com.examsample.ui.bookmark.local.BookmarkDatabase
import com.examsample.ui.bookmark.model.BookmarkModel
import com.examsample.ui.home.model.ProductModel
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
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

    fun selectDBProductExists(
        context: Context,
        productModel: ProductModel,
        result: (exists: Boolean, model: ProductModel) -> Unit,
        onFail: (error: String) -> Unit
        ): Disposable =
        BookmarkDatabase.getInstance(context).bookmarkDao().selectProductExists(productModel.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ exists ->
                Logger.d("selectDBProductExists exists >>> $exists")
                when (exists) {
                    1 -> result(true, productModel)
                    else -> result(false, productModel)
                }
            }, { error ->
                onFail(error.toString())
                Logger.d("selectDBProductExists error Log >>> $error")
            })

}