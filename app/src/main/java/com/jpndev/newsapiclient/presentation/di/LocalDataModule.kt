package com.jpndev.newsapiclient.presentation.di

import com.jpndev.newsapiclient.data.api.NewsAPIService
import com.jpndev.newsapiclient.data.db.ArticleDAO
import com.jpndev.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.jpndev.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.jpndev.newsapiclient.data.repository.dataSourceImpl.NewsLocalDataSourceImpl
import com.jpndev.newsapiclient.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDataModule {

    @Singleton
    @Provides
    fun provideNewsLocalDataSource(
        articleDAO: ArticleDAO
    ): NewsLocalDataSource {
       return NewsLocalDataSourceImpl(articleDAO)
    }

}












