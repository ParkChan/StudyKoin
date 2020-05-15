package com.examsample.network.data.model

import com.google.gson.annotations.SerializedName

data class DescriptionModel(

    @SerializedName("imagePath")
    val imagePath: Int,

    @SerializedName("subject")
    val subject: String,

    @SerializedName("price")
    val price: String
)
