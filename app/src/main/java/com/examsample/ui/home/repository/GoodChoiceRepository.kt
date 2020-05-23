package com.examsample.ui.home.repository

import com.examsample.ui.home.model.res.ResProductListModel
import com.examsample.ui.home.remote.SearchProductRemoteDataSource
import io.reactivex.disposables.Disposable

class GoodChoiceRepository(
    private val searchProductRemoteDataSource: SearchProductRemoteDataSource
) {
    fun requestData(
        page: Int,
        onSuccess: (resProductListModel: ResProductListModel) -> Unit,
        onFail: (error: String) -> Unit,
        isProgress: (isProgress: Boolean) -> Unit
    ): Disposable =
        searchProductRemoteDataSource.searchProductList(
            page,
            onSuccess = {
                onSuccess(it)
            },
            onFail = {
                onFail(it)
            }, isProgress = {
                isProgress(it)
            }
        )
}