package com.chan.di

import com.chan.ui.bookmark.local.BookmarkDataSource
import com.chan.ui.bookmark.repository.BookmarkRepository
import com.chan.ui.home.remote.SearchProductRemoteDataSource
import com.chan.ui.home.repository.GoodChoiceRepository
import org.koin.dsl.module

val dataSourceModules = module {
    single { GoodChoiceRepository(get()) }
    single { SearchProductRemoteDataSource(get()) }
    single { BookmarkRepository(get()) }
    single { BookmarkDataSource() }
}