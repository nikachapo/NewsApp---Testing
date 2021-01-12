package com.epam.newsapp

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharedPreferencesUtil(
    context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ISharedPreferencesUtil {

    override val preferences: SharedPreferences =
        context.getSharedPreferences("token", Context.MODE_PRIVATE)
    override val editor: SharedPreferences.Editor = preferences.edit()

    override suspend fun putString(key: String, value: String?): Unit = withContext(ioDispatcher) {
        editor.putString(key, value)
        editor.commit()
    }

    override suspend fun getString(key: String): String? = withContext(ioDispatcher) {
        preferences.getString(key, null)
    }

    companion object {
        const val KEY_UID = "k_id"
        const val KEY_TOKEN = "k_token"
        const val KEY_NAME = "k_name"
    }
}