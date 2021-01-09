package com.epam.newsapp.data

import com.epam.newsapp.data.model.LoggedInUser
import kotlinx.coroutines.delay
import java.io.IOException

object LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        delay(2000)
        return try {
            val user = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
            Result.Success(user)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    suspend fun logout() {
        delay(2000)
    }
}