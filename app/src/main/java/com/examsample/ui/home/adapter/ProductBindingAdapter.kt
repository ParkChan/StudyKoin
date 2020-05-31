package com.examsample.ui.home.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.examsample.ui.home.model.ProductModel
import com.orhanobut.logger.Logger


@BindingAdapter("productListData")
fun setProductListData(recyclerView: RecyclerView, items: List<ProductModel>?) {
    items?.let {
        if (recyclerView.adapter is ProductAdapter) {
            (recyclerView.adapter as ProductAdapter).addProductList(it)
        }
    }
}
