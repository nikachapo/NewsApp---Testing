package com.epam.newsapp.data.news

import com.epam.newsapp.data.news.model.NewsModel

interface NewsDataSource {
    suspend fun getNews(): List<NewsModel>
}
