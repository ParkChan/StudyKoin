package com.examsample.ui.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examsample.ui.bookmark.local.BookmarkDatabase
import com.examsample.ui.bookmark.model.BookmarkModel
import com.examsample.ui.bookmark.repository.BookMarkRepository
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel(
    private val bookMarkRepository: BookMarkRepository
) : ViewModel() {

    private val _exists = MutableLiveData<Boolean>()
    val exists = _exists

    fun selectDBProductExists(context: Context, productId: String): Disposable =
        BookmarkDatabase.getInstance(context).bookmarkDao().selectProductExists(productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ exists ->
                Logger.d("selectDBProductExists exists >>> $exists")
                when (exists) {
                    1 -> _exists.value = true
                    else -> _exists.value = false
                }
            }, { error ->
                Logger.d("selectDBProductExists error Log >>> $error")
            })

    fun deleteBookMark(context: Context, model: BookmarkModel) {
        bookMarkRepository.deleteBookMark(context, model)
    }

    fun insertBookMark(context: Context, model: BookmarkModel) {
        bookMarkRepository.insertBookMark(context, model)
    }
}