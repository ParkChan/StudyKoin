package com.examsample.ui.bookmark.viewmodel

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examsample.ui.bookmark.BookmarkSortType
import com.examsample.ui.bookmark.model.BookmarkModel
import com.examsample.ui.bookmark.repository.BookmarkRepository
import com.examsample.ui.home.model.DescriptionModel
import com.examsample.ui.home.model.ProductModel
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.reactivex.disposables.Disposable

class BookmarkViewModel(
    private val bookmarkRepository: BookmarkRepository,
    private val activityResultLauncher: ActivityResultLauncher<String>
) : ViewModel() {

    private val _bookmarkListData = MutableLiveData<List<BookmarkModel>>()
    var bookmarkListData: LiveData<List<BookmarkModel>> = _bookmarkListData

    private val _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    private val _removeBookmarkModel = MutableLiveData<BookmarkModel>()
    var removeBookmarkModel: LiveData<BookmarkModel> = _removeBookmarkModel

    private val _existsProductModel = MutableLiveData<ProductModel>()
    val existsProductModel = _existsProductModel


    //정렬 타입
    private val _sortType = MutableLiveData<BookmarkSortType>().apply {
        value = BookmarkSortType.RegDateDesc
    }
    var sortType: LiveData<BookmarkSortType> = _sortType

    var lastRequestSortType: BookmarkSortType = BookmarkSortType.RegDateDesc

    fun selectAll(context: Context, sortType: BookmarkSortType): Disposable =
        bookmarkRepository.selectAll(context,
            sortType,
            onSuccess = {
                lastRequestSortType = sortType
                _bookmarkListData.postValue(it)
            }, onFail = {
                _errorMessage.postValue(it)
            }
        )

    fun sendRemoveBookmarkData(model: BookmarkModel) {
        _removeBookmarkModel.postValue(model)
    }

    fun removeBookmark(context: Context, model: BookmarkModel) {
        bookmarkRepository.deleteBookMark(context, model)
        selectAll(context, lastRequestSortType)
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

    fun selectDBProductExists(context: Context, productModel: ProductModel): Disposable =
        bookmarkRepository.selectDBProductExists(
            context,
            productModel,
            result = { exists, model ->
                Logger.d("selectDBProductExists removeData $exists")
                if(!exists){
                    _existsProductModel.value = model
                }
            },
            onFail = {
                Logger.d("selectDBProductExists error Log >>> $it")
            }
        )
}