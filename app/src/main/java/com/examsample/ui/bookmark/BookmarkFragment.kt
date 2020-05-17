package com.examsample.ui.bookmark

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examsample.ExamSampleApplication
import com.examsample.R
import com.examsample.common.BaseFragment
import com.examsample.databinding.FragmentBookmarkBinding
import com.examsample.ui.bookmark.adapter.BookmarkAdapter
import com.examsample.ui.bookmark.viewmodel.BookmarkViewModel
import com.orhanobut.logger.Logger

class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(
    R.layout.fragment_bookmark
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        iniViewModelObserve()
        selectAllBookmarkList()
    }

    private fun initViewModel() {

        binding.bookmarkViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return BookmarkViewModel(context?.applicationContext as ExamSampleApplication) as T
            }
        }).get(BookmarkViewModel::class.java)

        binding.rvBookmark.adapter = BookmarkAdapter(binding.bookmarkViewModel as BookmarkViewModel)
    }

    private fun iniViewModelObserve() {
        binding.bookmarkViewModel?.bookmarkListData?.observe(viewLifecycleOwner, Observer {
            Logger.d("bookmarkViewModel observe listData $it")
        })
        binding.bookmarkViewModel?.errorMessage?.observe(viewLifecycleOwner, Observer {
            Logger.d("bookmarkViewModel observe errorMessage $it")
            showToast(getString(R.string.common_toast_msg_network_error))
        })

        //즐겨찾기 삭제
        binding.bookmarkViewModel?.removeBookmarkModel?.observe(viewLifecycleOwner, Observer {
            Logger.d("bookmarkViewModel observe removeData $it")
            binding.bookmarkViewModel?.removeBookmark(context, it)
        })

    }

    private fun selectAllBookmarkList(){
        binding.bookmarkViewModel?.let {
            compositeDisposable.add(it.selectAll())
        }
    }

}
