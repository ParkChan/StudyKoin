package com.chan.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chan.BR
import com.chan.databinding.ItemProductBinding
import com.chan.ui.home.model.ProductModel
import com.chan.ui.home.viewmodel.HomeViewModel


class ProductAdapter(
    private val homeViewModel: HomeViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val items = mutableListOf<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
             ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        binding.setVariable(BR.homeViewModel, homeViewModel)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CustomViewHolder){
            holder.bind(position, items[position])
        }
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
        return items.size
    }

    fun replaceItems(items: List<ProductModel>?) {
        items?.let {
            with(this.items) {
                clear()
                addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    fun addAllItems(items: List<ProductModel>?) {
        items?.let {
            with(this.items) {
                val positionStart = this.size
                addAll(it)
                notifyItemRangeInserted(positionStart, it.size)
            }
        }
    }
}