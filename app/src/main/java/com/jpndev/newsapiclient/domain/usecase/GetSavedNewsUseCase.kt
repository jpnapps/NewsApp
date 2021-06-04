package com.jpndev.newsapiclient.domain.usecase

import com.jpndev.newsapiclient.data.model.Article
import com.jpndev.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    fun execute(): Flow<List<Article>>{
        return newsRepository.getSavedNews()
    }
}