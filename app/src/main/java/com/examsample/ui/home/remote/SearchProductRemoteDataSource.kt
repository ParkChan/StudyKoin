package com.examsample.ui.home.remote

import com.examsample.network.NETWORK_SUCCESS_CODE
import com.examsample.network.api.GoodChoiceApi
import com.examsample.ui.home.model.res.ResProductListModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchProductRemoteDataSource(
    private val retrofit: GoodChoiceApi
) {
    fun searchProductList(
        page: Int,
        onSuccess: (githubInfo: ResProductListModel) -> Unit,
        onFail: (error: String) -> Unit,
        isProgress: (isProgress: Boolean) -> Unit
    ): Disposable = retrofit.getProductList(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally {
            isProgress(false)
        }
        .subscribe({
            isProgress(true)
            val response = it.body()
            if (it.isSuccessful && response != null && response.code == NETWORK_SUCCESS_CODE) {
                onSuccess(response)
            } else {
                onFail(it.message())
            }
        }, {
            onFail(it.message.toString())
            isProgress(false)
        })
}