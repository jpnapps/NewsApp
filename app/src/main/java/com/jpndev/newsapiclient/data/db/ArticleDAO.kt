package com.jpndev.newsapiclient.data.db

import androidx.room.*
import com.jpndev.newsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticle()

    @Delete
    suspend fun delete(article: Article)


    @Query("SELECT * FROM articles")
     fun getArticles(): Flow<List<Article>>
}