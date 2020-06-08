package com.chan.common.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<A : Any>
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val items get() = mutableListOf<A>()

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