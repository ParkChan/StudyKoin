package com.chan.di

import com.chan.common.viewmodel.BookmarkEventViewModel
import com.chan.ui.bookmark.viewmodel.BookmarkViewModel
import com.chan.ui.home.viewmodel.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelMudules = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { BookmarkViewModel(get()) }
    viewModel { BookmarkEventViewModel() }

}