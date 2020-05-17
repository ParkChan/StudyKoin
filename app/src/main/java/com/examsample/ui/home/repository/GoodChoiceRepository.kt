package com.examsample.ui.home.repository

import androidx.lifecycle.MutableLiveData
import com.examsample.network.NETWORK_ROW_COUNT
import com.examsample.ui.home.data.ResProductListData
import com.examsample.ui.home.model.ProductModel
import com.examsample.ui.home.remote.SearchProductRemoteDataSource
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable

class GoodChoiceRepository(
    private val compositeDisposable: CompositeDisposable,
    private val searchProductRemoteDataSource: SearchProductRemoteDataSource
) {
    private val responseData = MutableLiveData<List<ProductModel>>()
    private val errorMessage = MutableLiveData<String>()

    private val defaultTotalPageCnt = 1
    private val defaultStartPageNumber = 1

    private var requestedPage = defaultTotalPageCnt
    private var totalPage = defaultTotalPageCnt
    private var isRequestInProgress = false

    private val productList = mutableListOf<ProductModel>()

    fun requestFirst(): ResProductListData {
        initPageInfo()
        requestData(defaultStartPageNumber)
        return ResProductListData(
            responseData,
            errorMessage
        )
    }

    fun requestNext() {
        Logger.d("now Page >>> $requestedPage total Page >>> $totalPage")
        if (requestedPage > totalPage) return
        requestData(requestedPage)
    }

    private fun initPageInfo() {
        requestedPage = defaultTotalPageCnt
        totalPage = defaultTotalPageCnt
        productList.clear()
    }

    private fun requestData(page: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        compositeDisposable.add(searchProductRemoteDataSource.searchProductList(
            page,
            onSuccess = {

                val data = it.data
                val totalCount = data.totalCount

                totalPage = if (totalCount / NETWORK_ROW_COUNT > 0) {
                    (totalCount / NETWORK_ROW_COUNT) + 1
                } else {
                    defaultTotalPageCnt
                }

                it.data.productList.let {
                    productList.addAll(it)
                    responseData.postValue(productList)
                    requestedPage++
                }
                isRequestInProgress = false
            },
            onFail = {
                errorMessage.postValue(it)
                isRequestInProgress = false
            }
        ))
    }

}