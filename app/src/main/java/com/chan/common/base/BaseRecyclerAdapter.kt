package com.chan.common.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T>
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val items = mutableListOf<T>()

    override fun getItemCount(): Int {
        return items.size
    }

    fun replaceItems(items: List<T>?) {
        items?.let {
            with(this.items) {
                clear()
                addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    fun addAllItems(items: List<T>?) {
        items?.let {
            with(this.items) {
                val positionStart = this.size
                addAll(it)
                notifyItemRangeInserted(positionStart, it.size)
            }
        }
    }
}