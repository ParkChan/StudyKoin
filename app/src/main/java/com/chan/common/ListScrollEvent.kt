package com.chan.common

interface ListScrollEvent {
    fun onScrolled(visibleItemCount: Int, fistVisibleItem: Int, totalItemCount: Int)
}