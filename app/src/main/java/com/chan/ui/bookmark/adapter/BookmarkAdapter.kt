package com.chan.ui.bookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chan.BR
import com.chan.R
import com.chan.databinding.ItemBookmarkBinding
import com.chan.ui.bookmark.model.BookmarkModel
import com.chan.ui.bookmark.viewmodel.BookmarkViewModel

class BookmarkAdapter(
    private val bookmarkViewModel: BookmarkViewModel
) : ListAdapter<BookmarkModel, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemBookmarkBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_bookmark,
            parent,
            false
        )
        binding.setVariable(BR.bookmarkViewModel, bookmarkViewModel)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CustomViewHolder).bind(position, getItem(position))
    }

    class CustomViewHolder(
        private val binding: ItemBookmarkBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, model: BookmarkModel) {
            binding.setVariable(BR.itemPosition, position)
            binding.setVariable(BR.bookmarkModel, model)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<BookmarkModel>() {
            override fun areItemsTheSame(oldItem: BookmarkModel, newItem: BookmarkModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: BookmarkModel, newItem: BookmarkModel
            ): Boolean = oldItem == newItem
        }
    }
}