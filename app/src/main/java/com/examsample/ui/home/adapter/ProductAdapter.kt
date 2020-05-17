package com.examsample.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.examsample.BR
import com.examsample.R
import com.examsample.databinding.ItemProductBinding
import com.examsample.ui.home.model.ProductModel
import com.orhanobut.logger.Logger

class ProductAdapter : ListAdapter<ProductModel, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product,
            parent,
            false
        )
        return CustomViewHolder(
            binding,
            binding.root
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val productModel = getItem(position)
        productModel.also {
            Logger.d("position $position data >>> $productModel")
        }?.let {
            (holder as CustomViewHolder).bind(it)
        }
    }

    class CustomViewHolder(private val binding: ItemProductBinding, view: View) :
        RecyclerView.ViewHolder(view) {

        init {
//        view.setOnClickListener {
//            repo?.url?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
//            }
//        }
        }

        fun bind(repo: ProductModel) {
            binding.setVariable(BR.productModel, repo)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ProductModel>() {
            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean =
                oldItem == newItem
        }
    }
}