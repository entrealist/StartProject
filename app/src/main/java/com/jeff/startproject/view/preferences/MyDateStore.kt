package com.jeff.startproject.view.preferences

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class MyDateStore(context: Context) {

    companion object {
        const val DATA_STORE_NAME = "Jeff"
        val CONTENT_PREF_KEY = preferencesKey<String>("CONTENT")
    }

    private val dataStore = context.createDataStore(name = DATA_STORE_NAME)

    val profileFlow: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[CONTENT_PREF_KEY] ?: ""
        }

    suspend fun updateContent(content: String) {
        dataStore.edit {
            it[CONTENT_PREF_KEY] = content
        }
    }
}