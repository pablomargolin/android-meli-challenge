package com.margo.news_feed.di

import com.margo.news_feed.data.repository.NewsRepositoryImpl
import com.margo.news_feed.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module that binds the interface [NewsRepository] to its implementation [NewsRepositoryImpl].
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

}