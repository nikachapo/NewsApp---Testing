package com.epam.newsapp.data

import com.epam.newsapp.data.model.LoggedInUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class LoginRepository private constructor(private val dataSource: LoginDataSource) {

    private val _user = MutableStateFlow<LoggedInUser?>(null)

    val user: StateFlow<LoggedInUser?> = _user

    val isLoggedIn: Boolean
        get() = _user.value != null

    suspend fun logout() = withContext(Dispatchers.Default) {
        _user.value = null
        dataSource.logout()
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> =
        withContext(Dispatchers.Default) {
            val result = dataSource.login(username, password)

            if (result is Result.Success) {
                setLoggedInUser(result.data)
            }

            result
        }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this._user.value = loggedInUser
    }

    companion object {

        private var INSTANCE: LoginRepository? = null

        fun getInstance(dataSource: LoginDataSource): LoginRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = LoginRepository(dataSource)
                }
            }
            return INSTANCE!!
        }
    }
}