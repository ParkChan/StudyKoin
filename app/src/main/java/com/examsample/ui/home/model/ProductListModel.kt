package com.examsample.ui.home.model

import com.examsample.ui.home.model.ProductModel
import com.google.gson.annotations.SerializedName

data class ProductListModel(
    @SerializedName("totalCount")
    val totalCount: Int,

    @SerializedName("product")
    val productList: List<ProductModel>
)
