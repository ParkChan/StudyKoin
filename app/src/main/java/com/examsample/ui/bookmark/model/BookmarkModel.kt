package com.examsample.ui.bookmark.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "bookmarkTable")
data class BookmarkModel(

    @PrimaryKey @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("thumbnail")
    val thumbnail: String,

    @field:SerializedName("imagePath")
    val imagePath: String,

    @field:SerializedName("subject")
    val subject: String,

    @field:SerializedName("price")
    val price: Int,

    @field:SerializedName("rate")
    val rate: Float,

    @field:SerializedName("regTimeStamp")
    val regTimeStamp: Long

)
