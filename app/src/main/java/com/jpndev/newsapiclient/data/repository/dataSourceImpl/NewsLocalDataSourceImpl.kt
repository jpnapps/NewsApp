package com.jpndev.newsapiclient.data.repository.dataSourceImpl

import com.jpndev.newsapiclient.data.db.ArticleDAO
import com.jpndev.newsapiclient.data.model.Article
import com.jpndev.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
):NewsLocalDataSource {


    override suspend fun saveArticletoDb(item: Article) {
        articleDAO.insert(item)
    }

    override  fun getArticlesFromDB(): Flow<List<Article>> {
        return articleDAO.getArticles()
    }

    override suspend fun deleteArticle(item: Article) {
        articleDAO.delete(item)
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            articleDAO.deleteAllArticle()
        }
    }
}