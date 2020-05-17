package com.examsample.ui.bookmark.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.examsample.ExamSampleApplication
import com.examsample.ui.bookmark.local.BookmarkDatabase
import com.examsample.ui.bookmark.model.BookmarkModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BookmarkViewModel(
    application: ExamSampleApplication
) : AndroidViewModel(application) {

    private val bookmarkDatabase: BookmarkDatabase = BookmarkDatabase.getInstance(application)

    private var _bookmarkListData = MutableLiveData<List<BookmarkModel>>()
    var bookmarkListData: LiveData<List<BookmarkModel>> = _bookmarkListData

    private var _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    fun selectAll(): Disposable = bookmarkDatabase.bookmarkDao().selectAll()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ list ->
            _bookmarkListData.postValue(list)
        }, { error ->
            _errorMessage.postValue(error.message)
        })
}