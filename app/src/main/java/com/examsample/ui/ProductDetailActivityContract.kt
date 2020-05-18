package com.examsample.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class ProductDetailActivityContract : ActivityResultContract<String, String>() {

    companion object{
        const val EXTRA_INPUT_PRODUCT_DETAIL_KEY = "EXTRA_INPUT_PRODUCT_DETAIL_KEY"
        const val EXTRA_RESULT_PRODUCT_DETAIL_KEY = "EXTRA_RESULT_PRODUCT_DETAIL_KEY"
    }

    override fun createIntent(context: Context, input: String?): Intent {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(EXTRA_INPUT_PRODUCT_DETAIL_KEY, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return when (resultCode) {
            Activity.RESULT_OK -> intent?.getStringExtra(EXTRA_RESULT_PRODUCT_DETAIL_KEY)
            else -> null
        }
    }
}