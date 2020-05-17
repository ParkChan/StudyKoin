package com.examsample.ui.bookmark.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.examsample.BR
import com.examsample.R
import com.examsample.databinding.ItemBookmarkBinding
import com.examsample.ui.bookmark.model.BookmarkModel
import com.orhanobut.logger.Logger

class BookmarkAdapter : ListAdapter<BookmarkModel, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemBookmarkBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_bookmark,
            parent,
            false
        )
        return CustomViewHolder(
            binding,
            binding.root
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bookmarkModel = getItem(position)
        bookmarkModel.also {
            Logger.d("position $position data >>> $bookmarkModel")
        }?.let {
            (holder as CustomViewHolder).bind(it)
        }
    }

    class CustomViewHolder(private val binding: ItemBookmarkBinding, view: View) :
        RecyclerView.ViewHolder(view) {

        init {
//        view.setOnClickListener {
//            repo?.url?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
//            }
//        }
        }

        fun bind(repo: BookmarkModel) {
            binding.setVariable(BR.bookmarkModel, repo)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<BookmarkModel>() {
            override fun areItemsTheSame(oldItem: BookmarkModel, newItem: BookmarkModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BookmarkModel, newItem: BookmarkModel): Boolean =
                oldItem == newItem
        }
    }
}