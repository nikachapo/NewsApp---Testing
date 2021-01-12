package com.epam.newsapp.ui.login

import androidx.test.core.app.ApplicationProvider
import com.epam.newsapp.common.BaseCoroutinesTest
import com.epam.newsapp.data.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class LoginViewModelTest : BaseCoroutinesTest() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mockRepo: LoginRepository

    @Before
    fun setUp() {
        mockRepo = mock(LoginRepository::class.java)
        loginViewModel = LoginViewModel(
            mockRepo,
            ApplicationProvider.getApplicationContext())
    }

    @Test
    fun login() {

    }

    @Test
    fun loginDataChanged() {
    }

    @Test
    fun startUserSession() {
    }

    @Test
    fun checkUserLogin() {
    }
}