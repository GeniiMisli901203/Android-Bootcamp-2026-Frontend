package ru.sicampus.bootcamp2026.data.local


import android.content.SharedPreferences
import ru.sicampus.bootcamp2026.network.config.ApiConstants
import androidx.core.content.edit

class PreferencesManager(private val sharedPreferences: SharedPreferences) {

    fun saveToken(token: String) {
        sharedPreferences.edit {
            putString(ApiConstants.TOKEN_PREF_KEY, token)
        }
    }

    fun getToken(): String? {
        return sharedPreferences.getString(ApiConstants.TOKEN_PREF_KEY, null)
    }

    fun clearToken() {
        sharedPreferences.edit {
            remove(ApiConstants.TOKEN_PREF_KEY)
        }
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
}