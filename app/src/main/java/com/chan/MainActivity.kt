package com.chan

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.chan.common.ViewPagerAdapter
import com.chan.common.viewmodel.BookmarkEventViewModel
import com.chan.databinding.ActivityMainBinding
import com.chan.ui.bookmark.BookmarkFragment
import com.chan.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //AndroidInjection.inject(this@MainActivity)

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
