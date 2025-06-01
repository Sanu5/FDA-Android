package com.example.fda_android.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit
import com.example.fda_android.utils.Constants.PREFS_TOKEN_FILE
import com.example.fda_android.utils.Constants.USER_TOKEN

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String){
        prefs.edit {
            putString(USER_TOKEN, token)
        }
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun clearToken() {
        prefs.edit { remove(USER_TOKEN) }
    }

}
