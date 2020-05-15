package com.examsample.network.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.examsample.network.NETWORK_ROW_COUNT
import com.examsample.network.api.GoodChoiceApi
import com.examsample.network.data.ResProductListData
import com.examsample.network.data.model.ProductModel
import com.orhanobut.logger.Logger

class GoodChoiceRepository {

    private val searchProductRemoteDataSource =
        SearchProductRemoteDataSource(GoodChoiceApi.create())

    private val responseData = MutableLiveData<List<ProductModel>>()
    private val errorMessage = MutableLiveData<String>()

    private val defaultPageCount = 1
    private var lastRequestedPage = defaultPageCount
    private var totalPage = defaultPageCount
    private var isRequestInProgress = false

    private val productList = mutableListOf<ProductModel>()

    fun search(page: Int): ResProductListData {
        initPageInfo()
        requestData(page)
        return ResProductListData(responseData, errorMessage)
    }

    private fun initPageInfo() {
        lastRequestedPage = defaultPageCount
        totalPage = defaultPageCount
        productList.clear()
    }


    private fun requestData(page: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchProductRemoteDataSource.searchProductList(
            page,
            onSuccess = {

                val data = it.data
                val totalCount = data.totalCount

                totalPage = if (totalCount / NETWORK_ROW_COUNT > 0) {
                    (totalCount / NETWORK_ROW_COUNT) + 1
                } else {
                    defaultPageCount
                }

                it.data.productList.let {
                    productList.addAll(it)
                    responseData.postValue(productList)
                    lastRequestedPage++
                }
                isRequestInProgress = false
            },
            onFail = {
                errorMessage.postValue(it)
                isRequestInProgress = false
            }
        )
    }

    fun requestMore(page: Int) {
        Logger.d("now Page >>>$lastRequestedPage total Page >>>$totalPage")
        if (lastRequestedPage > totalPage || lastRequestedPage == totalPage) return
        requestData(page)
    }

}