package com.epam.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.epam.newsapp.NewsApplication
import com.epam.newsapp.R
import com.epam.newsapp.UserSession
import com.epam.newsapp.data.login.LoginRepository
import com.epam.newsapp.ui.login.LoginActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    private lateinit var loginRepository: LoginRepository
    private lateinit var userSession: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginRepository = (application as NewsApplication).loginRepository
        userSession = (application as NewsApplication).userSession

        lifecycleScope.launch { userSession.startSession() }

        lifecycleScope.launch {
            loginRepository.user.collect { loggedInUser ->
                if (loggedInUser != null) {
                    Toast.makeText(
                        applicationContext,
                        "${getString(R.string.welcome)} ${loggedInUser.displayName}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        lifecycleScope.launch {
            userSession.userSessionExpired.collect { sessionExpired ->
                if (sessionExpired) {
                    logOut()
                }
            }
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        lifecycleScope.launch {
            userSession.startSession()
        }
    }

    protected suspend fun logOut() {
        userSession.stopSession()
        loginRepository.logout()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}