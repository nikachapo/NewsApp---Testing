package com.epam.newsapp.data.news

import com.epam.newsapp.data.news.model.NewsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(private val newsDataSource: NewsDataSource) : NewsRepository {

    override fun fetchNews(): Flow<NewsModel> = flow {
        newsDataSource.getNews().forEach {
            emit(it)
        }
    }

}