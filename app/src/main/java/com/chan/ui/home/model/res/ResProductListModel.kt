package com.chan.ui.home.model.res

import com.chan.ui.home.model.ProductListModel
import com.google.gson.annotations.SerializedName

data class ResProductListModel(

    @SerializedName("code")
    val code: Int,

    @SerializedName("msg")
    val message: String,

    @SerializedName("data")
    val data: ProductListModel
)

