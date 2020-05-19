package com.examsample.ui.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examsample.ui.bookmark.model.BookmarkModel
import com.examsample.ui.bookmark.repository.BookMarkRepository
import com.examsample.ui.home.model.ProductModel
import com.orhanobut.logger.Logger
import io.reactivex.disposables.Disposable

class ProductDetailViewModel(
    private val bookMarkRepository: BookMarkRepository
) : ViewModel() {

    private val _exists = MutableLiveData<Boolean>()
    val exists = _exists

    fun selectDBProductExists(context: Context, productModel: ProductModel): Disposable =
        bookMarkRepository.selectDBProductExists(
            context,
            productModel,
            result = { exists, _ ->
                _exists.value = exists
            },
            onFail = {
                Logger.d("selectDBProductExists error Log >>> $it")
            }
        )

    fun deleteBookMark(context: Context, model: BookmarkModel) {
        bookMarkRepository.deleteBookMark(context, model)
    }

    fun insertBookMark(context: Context, model: BookmarkModel) {
        bookMarkRepository.insertBookMark(context, model)
    }
}