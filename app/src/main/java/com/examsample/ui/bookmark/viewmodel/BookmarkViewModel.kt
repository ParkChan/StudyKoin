package com.examsample.ui.bookmark.viewmodel

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.examsample.ExamSampleApplication
import com.examsample.ui.bookmark.local.BookmarkDatabase
import com.examsample.ui.bookmark.model.BookmarkModel
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarkViewModel(
    application: ExamSampleApplication
) : AndroidViewModel(application) {

    private val bookmarkDatabase: BookmarkDatabase = BookmarkDatabase.getInstance(application)

    private var _bookmarkListData = MutableLiveData<List<BookmarkModel>>()
    var bookmarkListData: LiveData<List<BookmarkModel>> = _bookmarkListData

    private var _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    private var _removeBookmarkModel = MutableLiveData<BookmarkModel>()
    var removeBookmarkModel: LiveData<BookmarkModel> = _removeBookmarkModel

    fun selectAll(): Disposable = bookmarkDatabase.bookmarkDao().selectAll()
        .subscribeOn(Schedulers.io())
        .subscribe({ list ->
            _bookmarkListData.postValue(list)
        }, { error ->
            _errorMessage.postValue(error.message)
        })

    fun sendRemoveBookmarkData(model: BookmarkModel) {
        _removeBookmarkModel.postValue(model)
    }

    fun removeBookmark(context: Context?, model: BookmarkModel){
        context?.let {
            CoroutineScope(Dispatchers.IO).launch {
                BookmarkDatabase.getInstance(context).bookmarkDao().delete(model)
                selectAll()
            }
        }
    }
}