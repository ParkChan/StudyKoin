package com.examsample.common

interface ListScrollEvent {
    fun onScrolled(visibleItemCount: Int, lastVisibleItem: Int, totalItemCount: Int)
}