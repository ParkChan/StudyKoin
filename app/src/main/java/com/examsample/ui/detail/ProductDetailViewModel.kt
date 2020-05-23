package com.examsample.ui.detail

import android.content.Context
import com.examsample.common.viewmodel.BaseViewModel
import com.examsample.ui.bookmark.repository.BookmarkRepository
import com.examsample.ui.home.model.ProductModel
import com.orhanobut.logger.Logger

class ProductDetailViewModel(
    private val bookmarkRepository: BookmarkRepository
) : BaseViewModel() {

    fun isBookMark(
        context: Context,
        productModel: ProductModel,
        onResult: (isBookMark: Boolean) -> Unit
    ) {
        compositeDisposable.add(bookmarkRepository.selectExists(
            context,
            productModel,
            result = { exists ->
                onResult(exists)
            }
        ))
    }

    fun onClickBookMark(context: Context, productModel: ProductModel) {
        Logger.d("onClickBookMark >>> $productModel")
        isBookMark(context, productModel, onResult = {
            if (it) {
                deleteBookMark(context, productModel)
            } else {
                insertBookMark(context, productModel)
            }
        })
    }

    private fun deleteBookMark(context: Context, model: ProductModel) {
        compositeDisposable.add(bookmarkRepository.deleteBookMark(context, model))

    }

    private fun insertBookMark(context: Context, model: ProductModel) {
        compositeDisposable.add(bookmarkRepository.insertBookMark(context, model))
    }

}