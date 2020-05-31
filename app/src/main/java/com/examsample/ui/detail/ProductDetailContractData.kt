package com.examsample.ui.detail

import com.examsample.ui.home.model.ProductModel
import java.io.Serializable

data class ProductDetailContractData(
    val position: Int,
    val productModel: ProductModel
) : Serializable