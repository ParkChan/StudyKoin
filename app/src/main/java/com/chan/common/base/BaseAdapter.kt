package com.chan.common.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<A : Any>(
    val binding: ViewDataBinding
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<A>()

    abstract fun onBindViewHolder(position: Int, item: Any?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        initViewHolder()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBindViewHolder(position, items[position])
    }

    protected fun initViewHolder(){}

    open class ViewHolder(
        binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        open fun onBindViewHolder(position: Int, item: Any?) {
            onBindViewHolder(position, item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun replaceItems(items: List<A>?) {
        items?.let {
            with(this.items) {
                clear()
                addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    fun addAllItems(items: List<A>?) {
        items?.let {
            with(this.items) {
                val positionStart = this.size
                addAll(it)
                notifyItemRangeInserted(positionStart, it.size)
            }
        }
    }
}