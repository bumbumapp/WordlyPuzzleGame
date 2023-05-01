package com.bumbumapps.khiardle.backend.models

import android.content.Context

class Languages(context: Context) {
    private var SHARD_PREFENCE = "Shared"

    val prefence = context.getSharedPreferences(SHARD_PREFENCE, Context.MODE_PRIVATE)

    fun getString(): String? {
        return prefence.getString("lan","English").toString()
    }

    fun setString(st: String) {
        val editor = prefence.edit()
        editor.putString("lan", st)
        editor.apply()
    }
}