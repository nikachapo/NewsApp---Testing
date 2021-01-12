package com.epam.newsapp.data.login

import com.epam.newsapp.ISharedPreferencesUtil
import com.epam.newsapp.SharedPreferencesUtil
import com.epam.newsapp.data.Result
import com.epam.newsapp.data.login.model.LoggedInUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.IOException

class LoginDataSource(
    private val sharedPreferencesUtil: ISharedPreferencesUtil,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun checkUserLogin(): Result<LoggedInUser> {
        val hasToken = (sharedPreferencesUtil
            .getString(SharedPreferencesUtil.KEY_TOKEN) ?: "").isNotEmpty()
        return if (hasToken) {
            val uid = sharedPreferencesUtil.getString(SharedPreferencesUtil.KEY_UID)
            val displayName = sharedPreferencesUtil.getString(SharedPreferencesUtil.KEY_NAME)
            if (uid != null && displayName != null) {
                Result.Success(LoggedInUser(uid, displayName))
            } else Result.Error(IOException("User data is not saved"))
        } else Result.Error(IOException("User has no token"))
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> =
        withContext(ioDispatcher) {
            try {
                val user = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
                val saveName = async {
                    sharedPreferencesUtil.putString(SharedPreferencesUtil.KEY_NAME, username)
                }
                val saveUid = async {
                    sharedPreferencesUtil.putString(SharedPreferencesUtil.KEY_UID, user.userId)
                }
                saveName.await()
                saveUid.await()
                Result.Success(user)
            } catch (e: Throwable) {
                Result.Error(IOException("Error logging in", e))
            }
        }

    suspend fun logout() {
        withContext(ioDispatcher) {
            sharedPreferencesUtil.putString(SharedPreferencesUtil.KEY_TOKEN, null)
            sharedPreferencesUtil.putString(SharedPreferencesUtil.KEY_UID, null)
            sharedPreferencesUtil.putString(SharedPreferencesUtil.KEY_NAME, null)
        }
    }
}