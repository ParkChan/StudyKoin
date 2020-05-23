package com.examsample.ui.home.viewmodel

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.MutableLiveData
import com.examsample.common.viewmodel.BaseViewModel
import com.examsample.network.NETWORK_ROW_COUNT
import com.examsample.ui.bookmark.repository.BookmarkRepository
import com.examsample.ui.home.model.ProductModel
import com.examsample.ui.home.repository.GoodChoiceRepository
import com.google.gson.Gson
import com.orhanobut.logger.Logger

class HomeViewModel(
    private val activityResultLauncher: ActivityResultLauncher<String>,
    private val goodChoiceRepository: GoodChoiceRepository,
    private val bookmarkRepository: BookmarkRepository
) : BaseViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 20
    }

    private val defaultTotalPageCnt = 1
    private val defaultStartPageNumber = 1

    private var requestePage = defaultTotalPageCnt
    private var totalPage = defaultTotalPageCnt
    private var isProgress = false

    private val _productListData = MutableLiveData<List<ProductModel>>()
    val productListData = _productListData

    private val _errorMessage: MutableLiveData<String> = MutableLiveData<String>()
    val errorMessage = _errorMessage

    private val listData = mutableListOf<ProductModel>()

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {

        Logger.d("listScrolled >>> $visibleItemCount $lastVisibleItemPosition $totalItemCount")

        if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
            if (isProgress || requestePage > totalPage) {
                return
            }
            requestNext()
        }
    }

    fun requestFirst() {
        initPageInfo()
        compositeDisposable.add(
            goodChoiceRepository.requestData(
                defaultStartPageNumber,
                onSuccess = {

                    listData.addAll(it.data.productList)
                    _productListData.value = listData

                    val totalCount = it.data.totalCount
                    totalPage = if (totalCount / NETWORK_ROW_COUNT > 0) {
                        (totalCount / NETWORK_ROW_COUNT) + 1
                    } else {
                        defaultTotalPageCnt
                    }
                    requestePage++
                },
                onFail = {
                    _errorMessage.value = it
                },
                isProgress = {
                    isProgress = it
                }
            )
        )
    }

    private fun requestNext() {
        //Logger.d("now Page >>> $requestedPage total Page >>> $totalPage")
        compositeDisposable.add(
            goodChoiceRepository.requestData(
                requestePage,
                onSuccess = {
                    listData.addAll(it.data.productList)
                    _productListData.value = listData
                    requestePage++
                },
                onFail = {
                    _errorMessage.value = it
                },
                isProgress = {
                    isProgress = it
                }
            )
        )
    }

    private fun initPageInfo() {
        requestePage = defaultTotalPageCnt
        totalPage = defaultTotalPageCnt
        listData.clear()
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