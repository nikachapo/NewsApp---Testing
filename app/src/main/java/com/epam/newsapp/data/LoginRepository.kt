package com.epam.newsapp.data

import com.epam.newsapp.data.model.LoggedInUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class LoginRepository(
    private val dataSource: LoginDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    private var userIsLoggedOut = false

    private val _user = MutableStateFlow<LoggedInUser?>(null)

    val user: StateFlow<LoggedInUser?> = _user

    suspend fun isLoggedIn(): Boolean {
        if (userIsLoggedOut) return false
        val result = dataSource.checkUserLogin()
        if (result is Result.Success) {
            _user.value = result.data
            return true
        }
        return false
    }

    suspend fun logout() {
        userIsLoggedOut = true
        setLoggedInUser(null)
        dataSource.logout()
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> =
        withContext(defaultDispatcher) {
            val result = dataSource.login(username, password)

            if (result is Result.Success) {
                setLoggedInUser(result.data)
            }

            result
        }.also { userIsLoggedOut = false }

    private fun setLoggedInUser(loggedInUser: LoggedInUser?) {
        _user.value = loggedInUser
    }
}