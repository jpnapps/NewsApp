package com.jpndev.newsapiclient.presentation.di

import com.jpndev.newsapiclient.domain.repository.NewsRepository
import com.jpndev.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.jpndev.newsapiclient.presentation.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdpaterModule {

   @Singleton
   @Provides
   fun provideGetNewsAdapter(

   ): NewsAdapter {
      return NewsAdapter()
   }
}


















