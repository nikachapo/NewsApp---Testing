@file:Suppress("ObjectPropertyName")

package com.epam.newsapp

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class UserSession(private val sharedPreferencesUtil: SharedPreferencesUtil) {

    private var sessionTimeCountJob: Job? = null

    private val _userSessionExpired = MutableStateFlow(false)

    val userSessionExpired: StateFlow<Boolean> = _userSessionExpired

    fun startSession() {
        _userSessionExpired.value = false
        sessionTimeCountJob?.cancel()

        sessionTimeCountJob = GlobalScope.launch {
            sharedPreferencesUtil.putString(
                SharedPreferencesUtil.KEY_TOKEN,
                java.util.UUID.randomUUID().toString()
            )
            val sessionTimeMillis = SESSION_TIME_M.toMillis()
            delay(sessionTimeMillis)
            _userSessionExpired.value = true

            /**
             * clear token after SESSION_TIME_M minutes
             */
            sharedPreferencesUtil.putString(
                SharedPreferencesUtil.KEY_TOKEN,
                ""
            )
        }
    }

    companion object {
        private const val SESSION_TIME_M = 1
    }
}

fun Int.toMillis(): Long {
    return (this * 60000).toLong()
}