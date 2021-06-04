package com.jpndev.newsapiclient.domain.usecase

import com.jpndev.newsapiclient.data.model.APIResponse
import com.jpndev.newsapiclient.data.util.Resource
import com.jpndev.newsapiclient.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country : String, page : Int): Resource<APIResponse>{
        return newsRepository.getNewsHeadlines(country,page)
    }
}