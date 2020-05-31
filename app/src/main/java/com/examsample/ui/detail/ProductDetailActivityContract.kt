package com.examsample.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.gson.Gson

class ProductDetailActivityContract :
    ActivityResultContract<ProductDetailContractData, ProductDetailContractData>() {

    companion object {
        const val EXTRA_PRODUCT_DATA_KEY = "EXTRA_PRODUCT_DATA_KEY"
    }

    override fun createIntent(
        context: Context,
        productDetailContractData: ProductDetailContractData?
    ): Intent {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(
            EXTRA_PRODUCT_DATA_KEY,
            Gson().toJson(productDetailContractData)
        )
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ProductDetailContractData? {
        return when (resultCode) {
            Activity.RESULT_OK -> Gson().fromJson(
                intent?.getStringExtra(EXTRA_PRODUCT_DATA_KEY),
                ProductDetailContractData::class.java
            )
            else -> null
        }
    }
}