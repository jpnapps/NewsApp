package com.jpndev.newsapiclient.data.repository.dataSource

import com.jpndev.newsapiclient.data.model.APIResponse
import com.jpndev.newsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsLocalDataSource {
    suspend fun saveArticletoDb(item : Article)
     fun getArticlesFromDB(): Flow<List<Article>>
    suspend fun deleteArticle(item : Article)
    suspend fun clearAll()
}