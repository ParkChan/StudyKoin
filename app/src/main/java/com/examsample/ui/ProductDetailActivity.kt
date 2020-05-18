package com.examsample.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.examsample.BR
import com.examsample.R
import com.examsample.common.BaseActivity
import com.examsample.databinding.ActivityProductDetailBinding
import com.examsample.ui.ProductDetailActivityContract.Companion.EXTRA_INPUT_PRODUCT_DETAIL_KEY
import com.examsample.ui.ProductDetailActivityContract.Companion.EXTRA_RESULT_PRODUCT_DETAIL_KEY
import com.examsample.ui.home.model.ProductModel
import com.google.gson.Gson

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
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra(EXTRA_RESULT_PRODUCT_DETAIL_KEY, "상세화면이 닫혔음") })
        finish()
    }
}