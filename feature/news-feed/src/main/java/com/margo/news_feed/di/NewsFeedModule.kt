package com.margo.news_feed.di

import com.margo.news_feed.data.remote.NewsFeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/**
 * Hilt module that provides API dependencies for the news feed feature.
 */
@Module
@InstallIn(SingletonComponent::class)
object NewsFeedModule {
    @Provides
    fun provideNewsFeedApi(retrofit: Retrofit): NewsFeedApi {
        return retrofit.create(NewsFeedApi::class.java)
    }
}