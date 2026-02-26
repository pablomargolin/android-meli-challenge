package com.margo.news_detail.di

import com.margo.news_detail.data.repository.NewsDetailRepositoryImpl
import com.margo.news_detail.domain.repository.NewsDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsDetailRepositoryModule {
    @Binds
    abstract fun bindNewsDetailRepository(
        newsDetailRepositoryImpl: NewsDetailRepositoryImpl
    ): NewsDetailRepository
}