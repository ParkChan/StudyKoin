package com.examsample.network.data.model.res

import com.examsample.network.data.model.ProductListModel
import com.google.gson.annotations.SerializedName

data class ResProductListModel(

    @SerializedName("code")
    val code: Int,

    @SerializedName("msg")
    val message: String,

    @SerializedName("data")
    val data: ProductListModel
)

