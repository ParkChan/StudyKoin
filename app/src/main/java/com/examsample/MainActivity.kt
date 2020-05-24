package com.examsample

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.examsample.common.BaseActivity
import com.examsample.common.ViewPagerAdapter
import com.examsample.common.viewmodel.BookmarkEventViewModel
import com.examsample.databinding.ActivityMainBinding
import com.examsample.ui.bookmark.BookmarkFragment
import com.examsample.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger

class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentList = listOf(HomeFragment(), BookmarkFragment())
        val pagerAdapter = ViewPagerAdapter(fragmentList, this)

        binding.viewpager.offscreenPageLimit = 2
        binding.viewpager.adapter = pagerAdapter
        val tabLayout = binding.tabLayout
        val tabTitleList =
            listOf(getString(R.string.title_home), getString(R.string.title_bookmark))
        TabLayoutMediator(tabLayout, binding.viewpager) { tab, position ->
            tab.text = tabTitleList[position]
        }.attach()

        binding.bookmarkEventViewModel =
            ViewModelProvider(this).get(BookmarkEventViewModel::class.java)

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 1) {
                    (pagerAdapter.list[1] as BookmarkFragment).listUpdate()
                }
            }
        })

        binding.bookmarkEventViewModel?.deleteProductModel?.observe(this, Observer {
            Logger.d("deleteProductModel observe >>> $it")
            (pagerAdapter.list[0] as HomeFragment).listUpdate(it)
        })

    }
}
