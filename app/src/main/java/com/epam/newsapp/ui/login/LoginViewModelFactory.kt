package com.epam.newsapp.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.epam.newsapp.data.LoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LoginViewModelFactory(
    private val loginRepository: LoginRepository,
    private val application: Application
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = loginRepository,
                application = application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}