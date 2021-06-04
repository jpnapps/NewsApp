 package com.jpndev.newsapiclient.presentation.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.jpndev.newsapiclient.data.db.AppDatabase
import com.jpndev.newsapiclient.data.db.ArticleDAO
import com.jpndev.newsapiclient.data.repository.NewsRepositoryImpl
import com.jpndev.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.jpndev.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.jpndev.newsapiclient.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {



    @Singleton
    @Provides
    fun providesDatabase(app: Application): AppDatabase {
        return  Room.databaseBuilder(app,
            AppDatabase::class.java,"jpndev_news_db").
            fallbackToDestructiveMigration().
        build()
    }

    @Singleton
    @Provides
    fun providesArticleDAO(database: AppDatabase): ArticleDAO {
        return  database.articleDao()
    }
}














