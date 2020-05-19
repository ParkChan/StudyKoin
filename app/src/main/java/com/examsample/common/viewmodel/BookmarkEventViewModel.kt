package com.examsample.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examsample.ui.bookmark.model.BookmarkModel
import com.examsample.ui.home.model.DescriptionModel
import com.examsample.ui.home.model.ProductModel

class BookmarkEventViewModel: ViewModel() {

    private val _deleteProductModel = MutableLiveData<ProductModel>()
    var deleteProductModel: LiveData<ProductModel> = _deleteProductModel

    fun deletedObserveBookmark(model: BookmarkModel){
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
            _deleteProductModel.value = this
        }
    }

    fun deletedObserveBookmark(model: ProductModel){
        _deleteProductModel.value = model
    }

}