package com.epam.newsapp.ui.login.models

data class LoginResult(
        val success: LoggedInUserView? = null,
        val error: Int? = null
)