package com.jpndev.newsapiclient.data.repository

import com.jpndev.newsapiclient.data.model.APIResponse
import com.jpndev.newsapiclient.data.model.Article
import com.jpndev.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.jpndev.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.jpndev.newsapiclient.data.util.Resource
import com.jpndev.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
        private val newsRemoteDataSource: NewsRemoteDataSource,
        private val newsLocalDataSource: NewsLocalDataSource
):NewsRepository {
    override suspend fun getNewsHeadlines(country : String, page : Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country,page))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedNews(country,searchQuery,page))
    }

    private fun responseToResource(response:Response<APIResponse>):Resource<APIResponse>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }




    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticletoDb(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticle(article)
    }

    override  fun getSavedNews(): Flow<List<Article>> {
      return  newsLocalDataSource.getArticlesFromDB()
    }
}