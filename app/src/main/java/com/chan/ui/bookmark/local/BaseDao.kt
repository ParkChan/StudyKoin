package com.chan.ui.bookmark.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Single


interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T): Completable

    @Delete
    fun delete(vararg obj: T): Single<Int>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(obj: T)

}