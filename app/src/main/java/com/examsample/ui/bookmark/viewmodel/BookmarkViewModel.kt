package com.examsample.ui.bookmark.viewmodel

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examsample.ui.bookmark.local.BookmarkDatabase
import com.examsample.ui.bookmark.model.BookmarkModel
import com.examsample.ui.bookmark.repository.BookMarkRepository
import com.examsample.ui.home.model.DescriptionModel
import com.examsample.ui.home.model.ProductModel
import com.google.gson.Gson
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BookmarkViewModel(
    private val bookMarkRepository: BookMarkRepository,
    private val activityResultLauncher: ActivityResultLauncher<String>,
    context: Context
) : ViewModel() {

    private val bookmarkDatabase: BookmarkDatabase = BookmarkDatabase.getInstance(context)

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

    fun removeBookmark(context: Context, model: BookmarkModel){
        bookMarkRepository.deleteBookMark(context, model)
        selectAll()
    }

    //상품 상세화면으로 이동
    fun startProductDetailActivity(model: BookmarkModel) {
        ProductModel(
            id = model.id,
            name = model.name,
            thumbnail = model.thumbnail,
            descriptionModel = DescriptionModel(
                imagePath = model.imagePath,
                subject = model.subject,
                price = model.price
            ),
            rate = model.rate
        ).run {
            activityResultLauncher.launch(Gson().toJson(this))
        }
    }
}