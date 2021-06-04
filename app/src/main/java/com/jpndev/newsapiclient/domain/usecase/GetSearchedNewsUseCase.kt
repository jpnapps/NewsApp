package com.jpndev.newsapiclient.domain.usecase

import com.jpndev.newsapiclient.data.model.APIResponse
import com.jpndev.newsapiclient.data.util.Resource
import com.jpndev.newsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
     suspend fun execute(country:String,searchQuery:String,page:Int): Resource<APIResponse>{
         return newsRepository.getSearchedNews(country,searchQuery,page)
     }
}