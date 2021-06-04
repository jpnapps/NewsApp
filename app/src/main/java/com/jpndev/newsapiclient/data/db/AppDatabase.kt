package com.jpndev.newsapiclient.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.jpndev.newsapiclient.data.model.Article


@Database(entities = [Article::class],
version = 1,
exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

abstract fun articleDao(): ArticleDAO

}