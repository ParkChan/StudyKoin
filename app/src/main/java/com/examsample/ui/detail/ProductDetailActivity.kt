package com.examsample.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examsample.R
import com.examsample.common.BaseActivity
import com.examsample.databinding.ActivityProductDetailBinding
import com.examsample.ui.bookmark.local.BookmarkDataSource
import com.examsample.ui.bookmark.repository.BookmarkRepository
import com.examsample.ui.home.model.ProductModel

/**
 * 상품 상세화면
 */
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(
    R.layout.activity_product_detail
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data: ProductDetailContractData =
            intent.getSerializableExtra(ProductDetailActivityContract.EXTRA_PRODUCT_DATA_KEY) as ProductDetailContractData
        binding.productModel?.apply {
            data.productModel
        }
        initViewModel()
        initLayoutComponent(data.productModel)

    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {

        binding.productDetailViewModel =
            ViewModelProvider(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return ProductDetailViewModel(BookmarkRepository(BookmarkDataSource())) as T
                }
            }).get(ProductDetailViewModel::class.java)

    }

    private fun initLayoutComponent(productModel: ProductModel) {
        binding.productDetailViewModel?.isBookMark(this, productModel,
            onResult = {
                binding.tbBookmark.isChecked = it
            })
    }

    override fun onBackPressed() {

        val data: ProductDetailContractData =
            intent.getSerializableExtra(
                ProductDetailActivityContract.EXTRA_PRODUCT_DATA_KEY
            ) as ProductDetailContractData

        setResult(
            Activity.RESULT_OK,
            Intent().apply {
                putExtra(
                    ProductDetailActivityContract.EXTRA_PRODUCT_DATA_KEY,
                    data
                )
            })
        finish()
    }
}