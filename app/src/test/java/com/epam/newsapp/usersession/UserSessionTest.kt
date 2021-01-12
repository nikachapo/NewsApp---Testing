package com.epam.newsapp.usersession

import com.epam.newsapp.ISharedPreferencesUtil
import com.epam.newsapp.SharedPreferencesUtil
import com.epam.newsapp.UserSession
import com.epam.newsapp.common.BaseCoroutinesTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class UserSessionTest : BaseCoroutinesTest() {

    private lateinit var userSession: UserSession
    private lateinit var mockSharedPreferencesUtil: ISharedPreferencesUtil

    @Before
    fun setUp() {
        mockSharedPreferencesUtil = mock(ISharedPreferencesUtil::class.java)
        userSession = UserSession(mockSharedPreferencesUtil)
    }

    @Test
    fun startSession() {
        mainCoroutineRule.runBlockingTest {
            assertThat(userSession.userSessionExpired.value, `is`(false))
            userSession.startSession()
            assertThat(userSession.userSessionExpired.value, `is`(true))
        }
    }

    @Test
    fun stopSession() {
        mainCoroutineRule.runBlockingTest {
            assertThat(userSession.userSessionExpired.value, `is`(false))
            userSession.stopSession()
            assertThat(userSession.userSessionExpired.value, `is`(true))
        }
    }
}