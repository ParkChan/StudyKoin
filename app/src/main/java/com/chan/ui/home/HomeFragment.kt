package com.chan.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.Observer
import com.chan.R
import com.chan.common.ListScrollEvent
import com.chan.common.base.BaseFragment
import com.chan.common.setRecyclerViewScrollListener
import com.chan.common.viewmodel.BookmarkEventViewModel
import com.chan.databinding.FragmentHomeBinding
import com.chan.ui.detail.ProductDetailActivityContract
import com.chan.ui.detail.ProductDetailContractData
import com.chan.ui.home.adapter.ProductListAdapter
import com.chan.ui.home.model.ProductModel
import com.chan.ui.home.viewmodel.HomeViewModel
import com.chan.utils.showToast
import com.orhanobut.logger.Logger
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    private val homeViewModel: HomeViewModel by viewModel()
    private val bookmarkEventViewModel by sharedViewModel<BookmarkEventViewModel>()

    private val activityResultLauncher: ActivityResultLauncher<ProductDetailContractData> =
        registerForActivityResult(
            ProductDetailActivityContract()
        ) { result: ProductDetailContractData ->
            (binding.rvProduct.adapter as ProductListAdapter).notifyItemChanged(result.position)
            Logger.d("registerForActivityResult >>> position is ${result.position} ")
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        iniViewModelObserve()
        initRecyclerViewPageEvent()
        requestFistPage()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        binding.homeViewModel = homeViewModel
        binding.bookmarkEventViewModel = bookmarkEventViewModel

        binding.rvProduct.adapter = ProductListAdapter(binding.homeViewModel as HomeViewModel)
    }

    private fun iniViewModelObserve() {
        binding.homeViewModel?.productListData?.observe(viewLifecycleOwner, Observer {
            Logger.d("homeViewModel observe listData $it")
        })
        binding.homeViewModel?.errorMessage?.observe(viewLifecycleOwner, Observer {
            Logger.d("homeViewModel observe errorMessage $it")
            context?.let { showToast(it, getString(R.string.common_toast_msg_network_error)) }
        })

        binding.homeViewModel?.productItemSelected?.observe(viewLifecycleOwner, Observer {
            activityResultLauncher.launch(ProductDetailContractData(it.position, it.productModel))
        })

        binding.bookmarkEventViewModel?.deleteProductModel?.observe(viewLifecycleOwner, Observer {
            Logger.d("bookmarkEventViewModel observe deleteProductModel $it")
            listUpdate(it)
        })
    }

    private fun initRecyclerViewPageEvent() {
        setRecyclerViewScrollListener(binding.rvProduct, object : ListScrollEvent {

            override fun onScrolled(
                visibleItemCount: Int,
                fistVisibleItem: Int,
                totalItemCount: Int
            ) {
                binding.homeViewModel?.listScrolled(
                    visibleItemCount,
                    fistVisibleItem,
                    totalItemCount
                )
            }
        })
    }

    private fun requestFistPage() {
        binding.homeViewModel?.requestFirst()
    }

    fun listUpdate(model: ProductModel) {
        val index = (binding.rvProduct.adapter as ProductListAdapter).items.indexOf(model)
        binding.rvProduct.adapter?.notifyItemChanged(index)
    }
}
