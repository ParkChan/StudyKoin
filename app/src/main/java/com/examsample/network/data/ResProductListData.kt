package com.examsample.network.data

import androidx.lifecycle.LiveData
import com.examsample.network.data.model.ProductModel
import com.examsample.network.data.model.res.ResProductListModel

data class ResProductListData(
    val productList: LiveData<List<ProductModel>>,
    val errorMessage: LiveData<String>
)

