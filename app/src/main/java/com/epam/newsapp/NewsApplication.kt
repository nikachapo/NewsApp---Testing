package com.epam.newsapp

import android.app.Application
import com.epam.newsapp.data.LoginDataSource
import com.epam.newsapp.data.LoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class NewsApplication : Application() {

    lateinit var loginRepository: LoginRepository
        private set

    lateinit var userSession: UserSession
        private set

    lateinit var sharedPreferencesUtil: SharedPreferencesUtil
        private set

    override fun onCreate() {
        super.onCreate()
        loginRepository = LoginRepository.getInstance(LoginDataSource)
        sharedPreferencesUtil = SharedPreferencesUtil(this)
        userSession = UserSession(sharedPreferencesUtil)
    }
}