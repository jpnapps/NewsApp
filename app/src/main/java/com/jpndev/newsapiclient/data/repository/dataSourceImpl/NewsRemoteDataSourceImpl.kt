package com.jpndev.newsapiclient.data.repository.dataSourceImpl

import com.jpndev.newsapiclient.data.api.NewsAPIService
import com.jpndev.newsapiclient.data.model.APIResponse
import com.jpndev.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
        private val newsAPIService: NewsAPIService
):NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country : String, page : Int): Response<APIResponse> {
          return newsAPIService.getTopHeadlines(country,page)
    }

    override suspend fun getSearchedNews(
        country: String,
        search_query: String,
        page: Int
    ): Response<APIResponse> {
        return newsAPIService.getSearchHeadlines(country,search_query,page)
    }
}