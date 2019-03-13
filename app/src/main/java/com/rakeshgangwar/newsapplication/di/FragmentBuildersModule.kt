package com.rakeshgangwar.newsapplication.di

import com.rakeshgangwar.newsapplication.ui.NewsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeNewsListFragment(): NewsListFragment
}