package com.chan.dagger.main

import androidx.databinding.DataBindingUtil
import com.chan.MainActivity
import com.chan.R
import com.chan.dagger.scope.ActivityScope
import com.chan.databinding.ActivityMainBinding
import dagger.Module
import dagger.Provides


@Module
abstract class MainActivityModule {

    companion object {
        @Provides
        @ActivityScope
        fun provideActivityMainBinding(activity: MainActivity): ActivityMainBinding {
            return DataBindingUtil.setContentView(activity, R.layout.activity_main)
        }
    }
}