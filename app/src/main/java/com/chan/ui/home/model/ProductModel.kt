package com.chan.ui.home.model

import com.google.gson.annotations.SerializedName

data class ProductModel(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("description")
    val descriptionModel: DescriptionModel,

    @SerializedName("rate")
    val rate: Float
)
