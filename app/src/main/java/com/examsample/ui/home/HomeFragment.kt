package com.examsample.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examsample.R
import com.examsample.common.BaseFragment
import com.examsample.common.ListScrollEvent
import com.examsample.databinding.FragmentHomeBinding
import com.examsample.network.api.GoodChoiceApi
import com.examsample.ui.home.adapter.ProductAdapter
import com.examsample.ui.home.remote.SearchProductRemoteDataSource
import com.examsample.ui.home.repository.GoodChoiceRepository
import com.examsample.ui.home.viewmodel.HomeViewModel
import com.orhanobut.logger.Logger

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    private val productAdapter = ProductAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvProduct.adapter = productAdapter

        initViewModel()
        iniViewModelObserve()
        initRecyclerViewPageEvent()
    }

    private fun initViewModel() {
        binding.homeViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(
                    GoodChoiceRepository(
                        compositeDisposable,
                        SearchProductRemoteDataSource(GoodChoiceApi.create())
                    )
                ) as T
            }
        }).get(HomeViewModel::class.java)
    }

    private fun iniViewModelObserve() {
        binding.homeViewModel?.productListData?.observe(viewLifecycleOwner, Observer {
            Logger.d("homeViewModel observe listData $it")
        })
        binding.homeViewModel?.errorMessage?.observe(viewLifecycleOwner, Observer {
            Logger.d("homeViewModel observe errorMessage $it")
            showToast(getString(R.string.common_toast_msg_network_error))
        })
    }

    private fun initRecyclerViewPageEvent() {
        setRecyclerViewScrollListener(binding.rvProduct, object : ListScrollEvent {

            override fun onScrolled(
                visibleItemCount: Int,
                lastVisibleItem: Int,
                totalItemCount: Int
            ) {
                binding.homeViewModel?.listScrolled(
                    visibleItemCount,
                    lastVisibleItem,
                    totalItemCount
                )
            }
        })
    }
}
