package com.examsample.ui.bookmark

sealed class BookMarkSortType {
    object RegDateDesc : BookMarkSortType()
    object RegDateAsc : BookMarkSortType()
    object ReviewRatingDesc : BookMarkSortType()
    object ReviewRatingAsc : BookMarkSortType()
}