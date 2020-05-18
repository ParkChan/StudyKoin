package com.examsample.ui.bookmark.local

import androidx.room.Dao
import androidx.room.Query
import com.examsample.ui.bookmark.model.BookmarkModel
import io.reactivex.Single

@Dao
interface BookmarkDao : BaseDao<BookmarkModel> {

    @Query("SELECT * FROM bookmarkTable")
    fun selectAll(): Single<List<BookmarkModel>>

    @Query("SELECT EXISTS (SELECT *FROM bookmarkTable where id = :productId)")
    fun selectProductExists(productId: String): Single<Int>
}

