package com.examsample.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import com.examsample.ui.bookmark.local.BookmarkDatabase
import com.examsample.ui.bookmark.repository.BookmarkRepository
import com.examsample.ui.home.model.ProductModel
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    fun isBookMark(
        context: Context,
        productId: String,
        onResult: (isBookMark: Boolean) -> Unit
    ): Disposable = BookmarkDatabase.getInstance(context)
        .bookmarkDao().selectProductExists(productId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ exists ->
            Logger.d("selectDBProductExists exists >>> $exists")
            onResult(exists == 1)
        }, { error ->
            Logger.d("selectDBProductExists error Log >>> $error")
        })

    fun onClickBookMark(context: Context, productModel: ProductModel) {
        Logger.d("onClickBookMark >>> $productModel")
        isBookMark(context, productModel.id, onResult = {
            if (it) {
                deleteBookMark(context, productModel)
            } else {
                insertBookMark(context, productModel)
            }
        })
    }

    private fun deleteBookMark(context: Context, model: ProductModel) {
        bookmarkRepository.deleteBookMark(context, model)
    }

    private fun insertBookMark(context: Context, model: ProductModel) {
        bookmarkRepository.insertBookMark(context, model)
    }
}