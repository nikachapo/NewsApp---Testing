package com.epam.newsapp.ui.login

import androidx.test.core.app.ApplicationProvider
import com.epam.newsapp.R
import com.epam.newsapp.common.BaseCoroutinesTest
import com.epam.newsapp.common.getOrAwaitValue
import com.epam.newsapp.data.login.LoginRepository
import com.epam.newsapp.data.Result
import com.epam.newsapp.data.login.model.LoggedInUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class LoginViewModelTest : BaseCoroutinesTest() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mockRepo: LoginRepository
    private val testLoggedInUser = LoggedInUser("testId", "testName")

    @Before
    fun setUp() {
        mockRepo = mock(LoginRepository::class.java)
        loginViewModel = LoginViewModel(
            mockRepo,
            ApplicationProvider.getApplicationContext()
        )
    }

    @Test
    fun `login - loginResult sets with proper data`() {
        mainCoroutineRule.runBlockingTest {
            `when`(mockRepo.login("testName", "testPass"))
                .thenReturn(Result.Success(testLoggedInUser))
            loginViewModel.login("testName", "testPass")
            assertThat(
                loginViewModel.loginResult.getOrAwaitValue().success?.displayName,
                `is`(testLoggedInUser.displayName)
            )
            `when`(mockRepo.login("testName", "testPass"))
                .thenReturn(Result.Error(Exception()))
            loginViewModel.login("testName", "testPass")
            assertThat(
                loginViewModel.loginResult.getOrAwaitValue().error, `is`(R.string.login_failed)
            )
        }
    }

    @Test
    fun `isUserNameValid test`() {
        var isValid = loginViewModel.isUserNameValid("username")
        assertThat(isValid, `is`(true))

        //should not be empty
        isValid = loginViewModel.isUserNameValid("")
        assertThat(isValid, `is`(false))

        //if contains `@` sign should match email
        isValid = loginViewModel.isUserNameValid("@com")
        assertThat(isValid, `is`(false))

        isValid = loginViewModel.isUserNameValid("testmail@gmail.com")
        assertThat(isValid, `is`(true))
    }

    @Test
    fun `isPasswordValid test`() {
        //password should contain more than 5 letter
        var isValid = loginViewModel.isPasswordValid("123")
        assertThat(isValid, `is`(false))

        isValid = loginViewModel.isPasswordValid("123456")
        assertThat(isValid, `is`(true))
    }

    @Test
    fun `checkUserLoggedIn test`() {
        mainCoroutineRule.runBlockingTest {
            `when`(mockRepo.isLoggedIn()).thenReturn(true)
            `when`(mockRepo.user).thenReturn(MutableStateFlow(testLoggedInUser))
            loginViewModel.checkUserLogin()
            assertThat(
                loginViewModel.loginResult.getOrAwaitValue().success?.displayName,
                `is`(testLoggedInUser.displayName)
            )
        }
    }
}