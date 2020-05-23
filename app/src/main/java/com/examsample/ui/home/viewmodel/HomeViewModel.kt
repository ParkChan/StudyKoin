package com.examsample.ui.home.viewmodel

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.examsample.ui.bookmark.local.BookmarkDatabase
import com.examsample.ui.bookmark.repository.BookmarkRepository
import com.examsample.ui.home.model.ProductModel
import com.examsample.ui.home.repository.GoodChoiceRepository
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val activityResultLauncher: ActivityResultLauncher<String>,
    private val goodChoiceRepository: GoodChoiceRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 20
    }

    private val requestFirst = MutableLiveData<Boolean>().apply {
        value = true
    }
    private val responseData = Transformations.map(requestFirst) {
        goodChoiceRepository.requestFirst()
    }
    val productListData =
        Transformations.switchMap(responseData) { it.productList }

    val errorMessage: LiveData<String> =
        Transformations.switchMap(responseData) { it.errorMessage }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            goodChoiceRepository.requestNext()
        }
    }

    //상품 상세화면으로 이동
    fun startProductDetailActivity(model: ProductModel) {
        activityResultLauncher.launch(Gson().toJson(model))
    }

    private fun insertBookMark(context: Context, model: ProductModel) {
        bookmarkRepository.insertBookMark(context, model).
    }

    private fun deleteBookMark(context: Context, model: ProductModel) {
        bookmarkRepository.deleteBookMark(context, model)
    }

    fun isBookMark(
        context: Context,
        productId: String,
        onResult: (isBookMark: Boolean) -> Unit
    ) {
        compositeDisposable.add(
            BookmarkDatabase.getInstance(context)
                .bookmarkDao().selectProductExists(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ exists ->
                    Logger.d("selectDBProductExists exists >>> $exists")
                    onResult(exists == 1)
                }, { error ->
                    Logger.d("selectDBProductExists error Log >>> $error")
                    onResult(false)
                })
        )
    }

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
}