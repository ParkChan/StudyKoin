package com.examsample.ui.bookmark

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examsample.R
import com.examsample.common.BaseFragment
import com.examsample.common.viewmodel.BookmarkEventViewModel
import com.examsample.databinding.FragmentBookmarkBinding
import com.examsample.ui.bookmark.adapter.BookmarkAdapter
import com.examsample.ui.bookmark.repository.BookmarkRepository
import com.examsample.ui.bookmark.viewmodel.BookmarkViewModel
import com.examsample.ui.detail.ProductDetailActivityContract
import com.examsample.ui.home.model.ProductModel
import com.google.gson.Gson
import com.orhanobut.logger.Logger

class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(
    R.layout.fragment_bookmark
) {
    private val activityResultLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ProductDetailActivityContract()
    ) { result: String? ->
        Logger.d("activity result >>> $result")
        val productModel = Gson().fromJson(result, ProductModel::class.java)
        //삭제유무 체크
        binding.bookmarkViewModel?.lastRequestSortType?.let { selectAllBookmarkList(it) }

        
        binding.bookmarkViewModel?.selectDBProductExists(binding.root.context, productModel)
            ?.let {
                compositeDisposable.add(it)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        iniViewModelObserve()
        initLayout()
        initListener()

    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        binding.bookmarkViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return BookmarkViewModel(
                    BookmarkRepository(),
                    activityResultLauncher
                ) as T
            }
        }).get(BookmarkViewModel::class.java)

        binding.bookmarkEventViewModel = activity?.let {
            ViewModelProvider(it).get(BookmarkEventViewModel::class.java)
        }

        binding.rvBookmark.adapter = BookmarkAdapter(binding.bookmarkViewModel as BookmarkViewModel)
    }

    private fun iniViewModelObserve() {

        //DB 리스트 조회 성공
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

        //DB 리스트 조회 실패
        binding.bookmarkViewModel?.errorMessage?.observe(viewLifecycleOwner, Observer {
            Logger.d("bookmarkViewModel observe errorMessage $it")
            showToast(getString(R.string.common_toast_msg_network_error))
        })

        binding.bookmarkViewModel?.sortType?.observe(viewLifecycleOwner, Observer {
            Logger.d("bookmarkViewModel observe sortType $it")
            selectAllBookmarkList(it)
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
                    binding.bookmarkEventViewModel?.deletedObserveBookmark(bookmarkModel)
                }
            })

        //상세화면에서 북마크 체크여부
        binding.bookmarkViewModel?.existsProductModel?.observe(viewLifecycleOwner, Observer {
            binding.bookmarkEventViewModel?.deletedObserveBookmark(it)
        })
    }

    private fun initLayout() {
        binding.rvBookmark.visibility = View.GONE
        binding.tvEmptyList.visibility = View.VISIBLE
    }

    private fun initListener() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            Logger.d("radioGroup checkedId $checkedId")

            when (checkedId) {
                R.id.radio_reg_date_desc -> {
                    binding.bookmarkViewModel?.selectAll(
                        binding.root.context,
                        BookmarkSortType.RegDateDesc
                    )
                }
                R.id.radio_reg_date_asc -> {
                    binding.bookmarkViewModel?.selectAll(
                        binding.root.context,
                        BookmarkSortType.RegDateAsc
                    )
                }
                R.id.radio_review_desc -> {
                    binding.bookmarkViewModel?.selectAll(
                        binding.root.context,
                        BookmarkSortType.ReviewRatingDesc
                    )
                }
                R.id.radio_review_asc -> {
                    binding.bookmarkViewModel?.selectAll(
                        binding.root.context,
                        BookmarkSortType.ReviewRatingAsc
                    )
                }
            }
            Handler().postDelayed({
                binding.rvBookmark.layoutManager?.scrollToPosition(0)
            }, 200)

        }
    }

    private fun selectAllBookmarkList(sortType: BookmarkSortType) {
        binding.bookmarkViewModel?.run {
            compositeDisposable.add(selectAll(binding.root.context, sortType))
        }
    }

    fun listUpdate() {
        binding.bookmarkViewModel?.lastRequestSortType?.let { selectAllBookmarkList(it) }
    }

}
