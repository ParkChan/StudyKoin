package com.examsample.ui.home.model

import com.google.gson.annotations.SerializedName

data class ProductModel(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("description")
    val description: DescriptionModel,

    @SerializedName("rate")
    val rate: Float


)
