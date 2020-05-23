package com.examsample.ui.home.viewmodel

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.examsample.common.viewmodel.BaseViewModel
import com.examsample.ui.bookmark.repository.BookmarkRepository
import com.examsample.ui.home.model.ProductModel
import com.examsample.ui.home.repository.GoodChoiceRepository
import com.google.gson.Gson

class HomeViewModel(
    private val activityResultLauncher: ActivityResultLauncher<String>,
    private val goodChoiceRepository: GoodChoiceRepository,
    private val bookmarkRepository: BookmarkRepository
) : BaseViewModel() {

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
        compositeDisposable.add(bookmarkRepository.insertBookMark(context, model))

    }

    private fun deleteBookMark(context: Context, model: ProductModel) {
        compositeDisposable.add(bookmarkRepository.deleteBookMark(context, model))
    }

    fun isBookMark(
        context: Context,
        productModel: ProductModel,
        onResult: (isBookMark: Boolean) -> Unit
    ) {
        bookmarkRepository.selectExists(
            context,
            productModel,
            result = { exists ->
                onResult(exists)
            }
        )
    }

    fun onClickBookMark(context: Context, productModel: ProductModel) {
        isBookMark(context, productModel, onResult = {
            if (it) {
                deleteBookMark(context, productModel)
            } else {
                insertBookMark(context, productModel)
            }
        })
    }
}