package com.examsample.ui.home

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.examsample.ui.home.model.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@BindingAdapter("listData")
fun setProductListData(recyclerView: RecyclerView, items: List<ProductModel>?) {
    if (recyclerView.adapter is ProductAdapter) {
        (recyclerView.adapter as ProductAdapter).submitList(items)
    }
//    else if (recyclerView.adapter is BookMarkAdapter) {
//        (recyclerView.adapter as BookMarkAdapter).addList(items)
//    }
}

@BindingAdapter("visibility")
fun setVisibility(view: View, text: String?) {
    if (text.isNullOrEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("bindImage")
fun setProductImage(view: ImageView,
    urlPath: String
) {
    Glide.with(view.context)
        .load(urlPath)
        .thumbnail(1.0f)
        .into(view)
}

//@BindingAdapter("addFavorite")
//fun addFavorite(view: View, model: ProductModel?) {
//    view.setOnClickListener {
//        model?.let {
//            Toast.makeText(view.context, "${model.name} add favorite!!", Toast.LENGTH_SHORT).show()
//            CoroutineScope(Dispatchers.IO).launch {
//                view.context?.let { GithubDatabase.getInstance(it)?.githubDao()?.insert(model) }
//            }
//        }
//    }
//}
//
//@BindingAdapter("deleteFavorite")
//fun deleteFavorite(view: View, model: ProductModel?) {
//    view.setOnClickListener {
//        model?.let {
//            Toast.makeText(view.context, "${model.name} delete favorite!!", Toast.LENGTH_SHORT)
//                .show()
//            CoroutineScope(Dispatchers.IO).launch {
//                GithubDatabase.getInstance(view.context).githubDao().delete(model)
//            }
//        }
//    }
//}
