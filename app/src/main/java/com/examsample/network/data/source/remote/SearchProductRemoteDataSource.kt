package com.examsample.network.data.source.remote

import com.examsample.network.NETWORK_SUCCESS_CODE
import com.examsample.network.api.GoodChoiceApi
import com.examsample.network.data.model.res.ResProductListModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchProductRemoteDataSource(
    private val retrofit: GoodChoiceApi
) {
    fun searchProductList(
        page: Int,
        onSuccess: (githubInfo: ResProductListModel) -> Unit,
        onFail: (error: String) -> Unit
    ): Disposable = retrofit.getProductList(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            if (it.isSuccessful) {
                val response = it.body()
                if (it.isSuccessful && response != null && response.code == NETWORK_SUCCESS_CODE) {
                    onSuccess(response)
                } else {
                    onFail(IllegalStateException("Please try again later").toString())
                }
            } else {
                onFail(it.message())
            }
        }, {
            onFail(it.message.toString())
        })
}