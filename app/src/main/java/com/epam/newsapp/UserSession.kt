@file:Suppress("ObjectPropertyName")

package com.epam.newsapp

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
object UserSession {

    private const val SESSION_TIME_M = 1

    private var sessionTimeCountJob: Job? = null

    private val _userSessionExpired = MutableStateFlow(false)

    val userSessionExpired: StateFlow<Boolean> = _userSessionExpired

    fun startSession() {
        _userSessionExpired.value = false
        sessionTimeCountJob?.cancel()

        sessionTimeCountJob = GlobalScope.launch {
            val sessionTimeMillis = SESSION_TIME_M.toMillis()
            delay(sessionTimeMillis)
            _userSessionExpired.value = true
        }
    }
}

fun Int.toMillis() :Long {
    return (this * 60000).toLong()
}