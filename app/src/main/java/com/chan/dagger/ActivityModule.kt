package com.chan.dagger

import com.chan.MainActivity
import com.chan.dagger.main.MainActivityModule
import com.chan.dagger.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class
        ]
    )
    abstract fun getMainActivity(): MainActivity
}
