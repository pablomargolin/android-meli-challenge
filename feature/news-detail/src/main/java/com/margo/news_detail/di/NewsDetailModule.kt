package com.margo.news_detail.di

import com.margo.news_detail.data.remote.NewsDetailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NewsDetailModule {
    @Provides
    fun provideSpaceFlightDetailApi(retrofit: Retrofit): NewsDetailApi {
        return retrofit.create(NewsDetailApi::class.java)
    }
}