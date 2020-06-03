package com.chan.ui.bookmark.local

import androidx.room.Dao
import androidx.room.Query
import com.chan.ui.bookmark.model.BookmarkModel
import io.reactivex.Single


@Dao
interface BookmarkDao : BaseDao<BookmarkModel> {

    @Query("SELECT * FROM bookmarkTable")
    fun selectAll(): Single<List<BookmarkModel>>

    //등록일 내림차순
    @Query("SELECT * FROM bookmarkTable ORDER BY regTimeStamp DESC")
    fun selectAllRegDateDesc(): Single<List<BookmarkModel>>

    //등록일 오름차순
    @Query("SELECT * FROM bookmarkTable ORDER BY regTimeStamp ASC")
    fun selectAllRegDateAsc(): Single<List<BookmarkModel>>

    //리뷰 내림차순
    @Query("SELECT * FROM bookmarkTable ORDER BY rate DESC")
    fun selectAllReviewDesc(): Single<List<BookmarkModel>>

    //리뷰 오름차순
    @Query("SELECT * FROM bookmarkTable ORDER BY rate ASC")
    fun selectAllReviewAsc(): Single<List<BookmarkModel>>

    //즐겨찾기 등록 유무
    @Query("SELECT EXISTS (SELECT *FROM bookmarkTable WHERE id = :productId)")
    fun selectProductExists(productId: String): Single<Int>

}

