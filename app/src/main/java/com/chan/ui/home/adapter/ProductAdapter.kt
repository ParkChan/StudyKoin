package com.chan.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chan.BR
import com.chan.R
import com.chan.databinding.ItemProductBinding
import com.chan.ui.home.model.ProductModel
import com.chan.ui.home.viewmodel.HomeViewModel

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
        binding.setVariable(BR.homeViewModel, homeViewModel)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CustomViewHolder).bind(position, productList[position])
    }

    class CustomViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, model: ProductModel) {
            binding.setVariable(BR.itemPosition, position)
            binding.setVariable(BR.productModel, model)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}