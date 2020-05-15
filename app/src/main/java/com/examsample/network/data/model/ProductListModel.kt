package com.examsample.network.data.model

import com.google.gson.annotations.SerializedName

data class ProductListModel(
    @SerializedName("totalCount")
    val totalCount: Int,

    @SerializedName("product")
    val productList: List<ProductModel>
)
