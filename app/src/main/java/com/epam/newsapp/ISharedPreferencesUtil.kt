package com.epam.newsapp

import android.content.SharedPreferences

interface ISharedPreferencesUtil {
    val preferences: SharedPreferences
    val editor: SharedPreferences.Editor

    suspend fun putString(key: String, value: String?): Unit

    suspend fun getString(key: String): String?
}