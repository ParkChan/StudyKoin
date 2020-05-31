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
import com.examsample.ui.home.viewmodel.HomeViewModel
import com.orhanobut.logger.Logger

class ProductAdapter(
    private val homeViewModel: HomeViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val productList = mutableListOf<ProductModel>()

    fun addProductList(list: List<ProductModel>) {
        productList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product,
            parent,
            false
        )
        return CustomViewHolder(
            binding,
            binding.root,
            homeViewModel
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val productModel = productList.get(position)
        productModel.also {
            Logger.d("position $position data >>> $productModel")
        }?.let {
            (holder as CustomViewHolder).bind(position, it)
        }
    }

    class CustomViewHolder(
        private val binding: ItemProductBinding,
        view: View,
        viewModel: HomeViewModel
    ) :
        RecyclerView.ViewHolder(view) {

        init {
            binding.homeViewModel = viewModel
        }

        fun bind(position: Int, model: ProductModel) {
            binding.setVariable(BR.itemPosition, position)
            binding.setVariable(BR.productModel, model)
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

    override fun getItemCount(): Int {
        return productList.size
    }
}