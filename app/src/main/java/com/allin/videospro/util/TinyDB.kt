package com.allin.videospro.util

import android.content.Context
import androidx.core.content.edit

class TinyDB(context: Context) {
    private val preferences = context.getSharedPreferences("TinyDB", Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        preferences.edit { putString(key, value) }
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return preferences.getString(key, defaultValue) ?: defaultValue
    }

    fun putInt(key: String,value: Int){
        preferences.edit { putInt(key, value) }
    }

    fun getInt(key: String,defaultValue: Int = 0): Int{
        return preferences.getInt(key,defaultValue)
    }

    fun putBoolean(key: String,value: Boolean){
        preferences.edit { putBoolean(key, value) }
    }

    fun getBoolean(key: String,defaultValue: Boolean = false): Boolean{
        return preferences.getBoolean(key,defaultValue)
    }
}