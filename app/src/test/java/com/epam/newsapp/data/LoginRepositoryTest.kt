package com.epam.newsapp.data

import com.epam.newsapp.common.BaseCoroutinesTest
import com.epam.newsapp.data.login.LoginDataSource
import com.epam.newsapp.data.login.LoginRepository
import com.epam.newsapp.data.login.model.LoggedInUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class LoginRepositoryTest : BaseCoroutinesTest() {

    private lateinit var loginRepository: LoginRepository
    private lateinit var mockLoginDataSource: LoginDataSource
    private val testUser = LoggedInUser("11", "testName")

    @Before
    fun setUp() {
        mockLoginDataSource = mock(LoginDataSource::class.java)
        loginRepository = LoginRepository(mockLoginDataSource, Dispatchers.Unconfined)
    }

    @Test
    fun `isLoggedIn - when mockLoginDataSource_checkUserLogin is Success - returns true`() {
        mainCoroutineRule.runBlockingTest {
            `when`(mockLoginDataSource.checkUserLogin()).thenReturn(Result.Success(testUser))
            assertThat(loginRepository.isLoggedIn(), `is`(true))
            assertThat(loginRepository.user.value, `is`(testUser))
        }
    }

    @Test
    fun `isLoggedIn - when mockLoginDataSource_checkUserLogin is Error - returns false`() {
        mainCoroutineRule.runBlockingTest {
            `when`(mockLoginDataSource.checkUserLogin()).thenReturn(Result.Error(Exception()))
            assertThat(loginRepository.isLoggedIn(), `is`(false))
        }
    }

    @Test
    fun `logout - user sets to null and LoginDataSource_logout is called`() {
        mainCoroutineRule.runBlockingTest {
            loginRepository.logout()
            assertThat(loginRepository.user.value, nullValue())
            verify(mockLoginDataSource).logout()
        }
    }

    @Test
    fun `login - user is set and LoginDataSource_login is called`() {
        mainCoroutineRule.runBlockingTest {
            `when`(mockLoginDataSource.login("testUser", "testPass"))
                .thenReturn(Result.Success(testUser))
            loginRepository.login("testUser", "testPass")
            assertThat(loginRepository.user.value, `is`(testUser))
            verify(mockLoginDataSource).login("testUser", "testPass")
        }
    }

    @Test
    fun `login - user is set to null when LoginDataSource_login returns Error`() {
        mainCoroutineRule.runBlockingTest {
            `when`(mockLoginDataSource.login("testUser", "testPass"))
                .thenReturn(Result.Error(Exception()))
            loginRepository.login("testUser", "testPass")
            assertThat(loginRepository.user.value, nullValue())
        }
    }

}