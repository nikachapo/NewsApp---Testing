package com.epam.newsapp

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharedPreferencesUtil(context: Context) {

    private val preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    suspend fun putString(key: String, value: String): Unit = withContext(Dispatchers.IO) {
        editor.putString(key, value)
        editor.commit()
    }

    suspend fun getString(key: String): String? = withContext(Dispatchers.IO) {
        preferences.getString(key, null)
    }

    companion object {
        const val KEY_UID = "k_id"
        const val KEY_TOKEN = "k_token"
        const val KEY_NAME = "k_name"
    }
}