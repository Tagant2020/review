package com.epitech.cashmanager.services

import android.content.Context
import android.content.SharedPreferences
import com.epitech.cashmanager.R

class SessionService(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val TOKEN = "token"
    }

    fun setToken(token: String) {
        val editor = prefs.edit()

        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN, null)
    }
}