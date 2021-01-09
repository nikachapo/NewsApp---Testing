package com.epam.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.epam.newsapp.data.LoginRepository
import com.epam.newsapp.ui.login.LoginActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    private lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginRepository = (application as NewsApplication).loginRepository
        lifecycleScope.launch {
            loginRepository.user.collect { loggedInUser ->
                if (loggedInUser == null) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.logged_out_automatically),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "${getString(R.string.welcome)} ${loggedInUser.displayName}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        lifecycleScope.launch {
            UserSession.userSessionExpired.collect { sessionExpired ->
                if (sessionExpired) {
                    logOut()
                }
            }
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        UserSession.startSession()
    }

    protected fun logOut() {
        lifecycleScope.launch { loginRepository.logout() }
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}