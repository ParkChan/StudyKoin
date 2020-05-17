package com.examsample.ui.bookmark.local

import androidx.room.Dao
import androidx.room.Query
import com.examsample.ui.bookmark.model.BookmarkModel
import io.reactivex.Maybe

@Dao
interface BookmarkDao : BaseDao<BookmarkModel> {

    @Query("SELECT * FROM bookmarkTable")
    fun selectAll(): Maybe<List<BookmarkModel>>
}

