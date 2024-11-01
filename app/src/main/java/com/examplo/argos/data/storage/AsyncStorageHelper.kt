package com.examplo.argos.data.storage

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsyncStorageHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("argos_storage", Context.MODE_PRIVATE)

    suspend fun saveString(key: String, value: String) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit().putString(key, value).apply()
        }
    }

    suspend fun getString(key: String, defaultValue: String = ""): String {
        return withContext(Dispatchers.IO) {
            sharedPreferences.getString(key, defaultValue) ?: defaultValue
        }
    }
}