package com.epam.newsapp.data.news

import com.epam.newsapp.data.news.model.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun fetchNews(): Flow<NewsModel>
}