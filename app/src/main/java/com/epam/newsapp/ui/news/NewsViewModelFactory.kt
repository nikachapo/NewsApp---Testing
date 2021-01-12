package com.epam.newsapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.epam.newsapp.data.news.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class NewsViewModelFactory(
    private val newsRepository: NewsRepository
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(
                newsRepository = newsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}