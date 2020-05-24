package com.examsample.common

interface ListScrollEvent {
    fun onScrolled(visibleItemCount: Int, fistVisibleItem: Int, totalItemCount: Int)
}