package com.epam.newsapp

import android.app.Application
import com.epam.newsapp.data.login.LoginDataSource
import com.epam.newsapp.data.login.LoginRepository
import com.epam.newsapp.data.news.FakeNewsDataSource
import com.epam.newsapp.data.news.NewsRepository
import com.epam.newsapp.data.news.NewsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class NewsApplication : Application() {

    lateinit var newsRepo: NewsRepository
        private set

    lateinit var loginRepository: LoginRepository
        private set

    lateinit var userSession: UserSession
        private set

    lateinit var sharedPreferencesUtil: SharedPreferencesUtil
        private set

    override fun onCreate() {
        super.onCreate()
        sharedPreferencesUtil = SharedPreferencesUtil(this)
        loginRepository = LoginRepository(LoginDataSource(sharedPreferencesUtil))
        userSession = UserSession(sharedPreferencesUtil)
        newsRepo = NewsRepositoryImpl(FakeNewsDataSource())
    }
}