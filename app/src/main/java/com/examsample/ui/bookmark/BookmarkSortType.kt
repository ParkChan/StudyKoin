package com.examsample.ui.bookmark

sealed class BookmarkSortType {
    object RegDateDesc : BookmarkSortType()
    object RegDateAsc : BookmarkSortType()
    object ReviewRatingDesc : BookmarkSortType()
    object ReviewRatingAsc : BookmarkSortType()
}