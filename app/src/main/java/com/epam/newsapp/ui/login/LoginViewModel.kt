package com.epam.newsapp.ui.login

import android.app.Application
import android.util.Patterns
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.epam.newsapp.R
import com.epam.newsapp.data.login.LoginRepository
import com.epam.newsapp.data.Result
import com.epam.newsapp.ui.login.models.LoggedInUserView
import com.epam.newsapp.ui.login.models.LoginFormState
import com.epam.newsapp.ui.login.models.LoginResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class LoginViewModel constructor(
    private val loginRepository: LoginRepository,
    application: Application) : AndroidViewModel(application) {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.login(username, password)
            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            if (username.isNotEmpty()) {
                _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
            }
        } else if (!isPasswordValid(password)) {
            if (password.isNotEmpty()) {
                _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            }
        } else {
            viewModelScope.launch {
                _loginForm.value = LoginFormState(isDataValid = true)
            }
        }
    }

    @VisibleForTesting
    fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    @VisibleForTesting
    fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    fun checkUserLogin() {
        viewModelScope.launch {
            if (loginRepository.isLoggedIn()) {
                loginRepository.user.collect {
                    if (it == null) return@collect
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = it.displayName))
                }
            }
        }
    }

}