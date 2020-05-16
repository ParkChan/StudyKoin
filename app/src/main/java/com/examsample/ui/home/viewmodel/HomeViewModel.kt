package com.examsample.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.examsample.ui.home.repository.GoodChoiceRepository

class HomeViewModel(
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
}