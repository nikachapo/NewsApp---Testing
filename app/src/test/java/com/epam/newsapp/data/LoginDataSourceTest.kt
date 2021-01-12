package com.epam.newsapp.data

import com.epam.newsapp.ISharedPreferencesUtil
import com.epam.newsapp.common.BaseCoroutinesTest
import com.epam.newsapp.SharedPreferencesUtil
import com.epam.newsapp.data.model.LoggedInUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class LoginDataSourceTest : BaseCoroutinesTest() {

    private lateinit var loginDataSource: LoginDataSource
    private lateinit var mockSharedPreferencesUtil: ISharedPreferencesUtil

    @Before
    fun setUp() {
        mockSharedPreferencesUtil = mock(ISharedPreferencesUtil::class.java)
        loginDataSource = LoginDataSource(mockSharedPreferencesUtil, Dispatchers.Unconfined)
    }

    @Test
    fun `checkUserLogin - when token does not exist - returns Result_Error`() {
        mainCoroutineRule.runBlockingTest {
            `when`(mockSharedPreferencesUtil.getString(SharedPreferencesUtil.KEY_TOKEN))
                .thenReturn(null)
            assertThat(loginDataSource.checkUserLogin(), instanceOf(Result.Error::class.java))
        }
    }

    @Test
    fun `checkUserLogin - when token exists but data is does not - returns Result_Error`() {
        mainCoroutineRule.runBlockingTest {
            `when`(mockSharedPreferencesUtil.getString(SharedPreferencesUtil.KEY_TOKEN))
                .thenReturn("1111111111")
            assertThat(loginDataSource.checkUserLogin(), instanceOf(Result.Error::class.java))
        }
    }

    @Test
    fun `checkUserLogin - when token exists but data is does not - returns Result_Success`() {
        mainCoroutineRule.runBlockingTest {
            `when`(mockSharedPreferencesUtil.getString(SharedPreferencesUtil.KEY_TOKEN))
                .thenReturn("1111111111")
            `when`(mockSharedPreferencesUtil.getString(SharedPreferencesUtil.KEY_UID))
                .thenReturn("1")
            `when`(mockSharedPreferencesUtil.getString(SharedPreferencesUtil.KEY_NAME))
                .thenReturn("testName")
            val result = loginDataSource.checkUserLogin()
            assertThat(result, instanceOf(Result.Success::class.java))
            assertThat((result as Result.Success<LoggedInUser>).data.userId, `is`("1"))
            assertThat(result.data.displayName, `is`("testName"))
        }
    }


    @Test
    fun `login - id and name is saved`() {
        mainCoroutineRule.runBlockingTest {
            val result = loginDataSource.login("testName", "testPass")
            val user = (result as Result.Success<LoggedInUser>).data
            verify(mockSharedPreferencesUtil).putString(
                SharedPreferencesUtil.KEY_NAME,
                user.displayName
            )
            verify(mockSharedPreferencesUtil).putString(SharedPreferencesUtil.KEY_UID, user.userId)
        }
    }


    @Test
    fun `logout - token is cleared`() {
        mainCoroutineRule.runBlockingTest {
            loginDataSource.logout()
            verify(mockSharedPreferencesUtil).putString(SharedPreferencesUtil.KEY_TOKEN, null)
            verify(mockSharedPreferencesUtil).putString(SharedPreferencesUtil.KEY_UID, null)
            verify(mockSharedPreferencesUtil).putString(SharedPreferencesUtil.KEY_NAME, null)
        }
    }
}