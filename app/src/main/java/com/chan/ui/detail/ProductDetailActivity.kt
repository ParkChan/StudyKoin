package com.chan.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chan.BR
import com.chan.R
import com.chan.common.base.BaseActivity
import com.chan.databinding.ActivityProductDetailBinding
import com.chan.ui.bookmark.local.BookmarkDataSource
import com.chan.ui.bookmark.repository.BookmarkRepository
import com.google.gson.Gson

/**
 * 상품 상세화면
 */
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(
    R.layout.activity_product_detail
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productModelStr =
            intent.getStringExtra(ProductDetailActivityContract.EXTRA_PRODUCT_DATA_KEY)
        val data: ProductDetailContractData =
            Gson().fromJson(productModelStr, ProductDetailContractData::class.java)
        binding.setVariable(BR.productModel, data.productModel)
        initViewModel()
        initLayoutComponent()

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

    private fun initLayoutComponent() {
        val model = binding.productModel
        model?.run {
            binding.productDetailViewModel?.isBookMark(binding.root.context, model,
                onResult = {
                    binding.tbBookmark.isChecked = it
                })
        }

    }

    override fun onBackPressed() {

        val productModelStr =
            intent.getStringExtra(ProductDetailActivityContract.EXTRA_PRODUCT_DATA_KEY)

        setResult(
            Activity.RESULT_OK,
            Intent().apply {
                putExtra(
                    ProductDetailActivityContract.EXTRA_PRODUCT_DATA_KEY,
                    productModelStr
                )
            })
        finish()
    }
}