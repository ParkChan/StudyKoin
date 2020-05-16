package com.examsample.ui.home.data

import androidx.lifecycle.LiveData
import com.examsample.ui.home.model.ProductModel

data class ResProductListData(
    val productList: LiveData<List<ProductModel>>,
    val errorMessage: LiveData<String>
)

