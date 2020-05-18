package com.examsample.ui.bookmark

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examsample.R
import com.examsample.common.BaseFragment
import com.examsample.databinding.FragmentBookmarkBinding
import com.examsample.ui.bookmark.adapter.BookmarkAdapter
import com.examsample.ui.bookmark.repository.BookMarkRepository
import com.examsample.ui.bookmark.viewmodel.BookmarkViewModel
import com.examsample.ui.detail.ProductDetailActivityContract
import com.orhanobut.logger.Logger

class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(
    R.layout.fragment_bookmark
) {
    private val activityResultLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ProductDetailActivityContract()
    ) { result: String? ->
        result?.let {
            Logger.d("activity result >>> $it")
            selectAllBookmarkList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        iniViewModelObserve()
        initLayout()
        selectAllBookmarkList()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        binding.bookmarkViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return context?.let {
                    BookmarkViewModel(
                        BookMarkRepository(),
                        activityResultLauncher,
                        it
                    )
                } as T
            }
        }).get(BookmarkViewModel::class.java)

        binding.rvBookmark.adapter = BookmarkAdapter(binding.bookmarkViewModel as BookmarkViewModel)
    }

    private fun iniViewModelObserve() {
        binding.bookmarkViewModel?.bookmarkListData?.observe(viewLifecycleOwner, Observer {
            Logger.d("bookmarkViewModel observe listData $it")
            if (it.isNotEmpty()) {
                binding.rvBookmark.visibility = View.VISIBLE
                binding.tvEmptyList.visibility = View.GONE

            } else {
                binding.rvBookmark.visibility = View.GONE
                binding.tvEmptyList.visibility = View.VISIBLE
            }

        })
        binding.bookmarkViewModel?.errorMessage?.observe(viewLifecycleOwner, Observer {
            Logger.d("bookmarkViewModel observe errorMessage $it")
            showToast(getString(R.string.common_toast_msg_network_error))
        })

        //즐겨찾기 삭제
        binding.bookmarkViewModel?.removeBookmarkModel?.observe(
            viewLifecycleOwner,
            Observer { bookmarkModel ->
                Logger.d("bookmarkViewModel observe removeData $bookmarkModel")
                context?.let { context ->
                    binding.bookmarkViewModel?.removeBookmark(
                        context,
                        bookmarkModel
                    )
                }
            })

    }

    private fun initLayout() {
        binding.tvEmptyList.visibility = View.GONE
    }

    private fun selectAllBookmarkList() {
        binding.bookmarkViewModel?.let {
            compositeDisposable.add(it.selectAll())
        }
    }

}
