package com.jpndev.newsapiclient.domain.usecase

import com.jpndev.newsapiclient.data.model.Article
import com.jpndev.newsapiclient.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
  suspend fun execute(article: Article)=newsRepository.saveNews(article)
}