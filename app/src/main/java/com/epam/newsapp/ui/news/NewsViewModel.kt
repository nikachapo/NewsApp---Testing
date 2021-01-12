package com.epam.newsapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.epam.newsapp.data.news.NewsRepository
import com.epam.newsapp.data.news.model.NewsModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class NewsViewModel(newsRepository: NewsRepository) : ViewModel() {

    val news: LiveData<NewsModel> = newsRepository.fetchNews().asLiveData()

}