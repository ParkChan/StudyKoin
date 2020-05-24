package com.examsample.ui.home.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.examsample.ui.home.model.ProductModel
import com.orhanobut.logger.Logger


@BindingAdapter("productListData")
fun setProductListData(recyclerView: RecyclerView, items: List<ProductModel>?) {
    items?.let {
        if (recyclerView.adapter is ProductAdapter) {
            val befortListSize = (recyclerView.adapter as ProductAdapter).currentList.size
            Logger.d("productListData >>>> $befortListSize ${items.size}")
            (recyclerView.adapter as ProductAdapter).submitList(it)
            (recyclerView.adapter as ProductAdapter).notifyDataSetChanged()
        }
    }
}
