package com.chan.ui.home.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chan.ui.home.model.ProductModel


@BindingAdapter("productListData")
fun setProductListData(recyclerView: RecyclerView, items: List<ProductModel>?) {
    items?.let {
        if (recyclerView.adapter is ProductAdapter) {
            (recyclerView.adapter as ProductAdapter).addProductList(it)
        }
    }
}
