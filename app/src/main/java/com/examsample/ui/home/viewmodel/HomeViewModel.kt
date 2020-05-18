package com.examsample.ui.home.viewmodel

import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.examsample.ui.home.model.ProductModel
import com.examsample.ui.home.repository.GoodChoiceRepository
import com.google.gson.Gson

class HomeViewModel(
    private val activityResultLauncher: ActivityResultLauncher<String>,
    private val goodChoiceRepository: GoodChoiceRepository
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
}