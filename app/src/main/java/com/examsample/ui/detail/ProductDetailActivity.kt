package com.examsample.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examsample.BR
import com.examsample.R
import com.examsample.common.BaseActivity
import com.examsample.databinding.ActivityProductDetailBinding
import com.examsample.ui.detail.ProductDetailActivityContract.Companion.EXTRA_INPUT_PRODUCT_DETAIL_KEY
import com.examsample.ui.detail.ProductDetailActivityContract.Companion.EXTRA_RESULT_PRODUCT_DETAIL_KEY
import com.examsample.ui.home.model.ProductModel
import com.google.gson.Gson
import com.orhanobut.logger.Logger

/**
 * 상품 상세화면
 */
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(
    R.layout.activity_product_detail
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productModelStr = intent.getStringExtra(EXTRA_INPUT_PRODUCT_DETAIL_KEY)
        val productModel: ProductModel = Gson().fromJson(productModelStr, ProductModel::class.java)
        binding.setVariable(BR.productModel, productModel)

        initViewModel()
        iniViewModelObserve()
        initLayoutComponent()

        binding.productDetailViewModel?.let {
            compositeDisposable.add(
                it.selectDBProductExists(
                    this, productModel.id
                )
            )
        }

    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {

        binding.productDetailViewModel =
            ViewModelProvider(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return ProductDetailViewModel() as T
                }
            }).get(ProductDetailViewModel::class.java)

    }

    private fun iniViewModelObserve() {
        binding.productDetailViewModel?.exists?.observe(this, Observer {
            Logger.d("productDetailViewModel observe exists $it")
            binding.tbIsBookmark.isChecked = it
        })
    }

    private fun initLayoutComponent() {

    }


    override fun onBackPressed() {
        setResult(
            Activity.RESULT_OK,
            Intent().apply { putExtra(EXTRA_RESULT_PRODUCT_DETAIL_KEY, "상세화면이 닫혔음") })
        finish()
    }
}