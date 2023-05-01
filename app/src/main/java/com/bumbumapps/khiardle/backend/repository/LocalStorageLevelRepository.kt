package com.bumbumapps.khiardle.backend.repository

import android.content.SharedPreferences
import com.bumbumapps.khiardle.backend.models.Level
import kotlin.math.max

class LocalStorageLevelRepository(
    private val sharedPreferences: SharedPreferences,
) : LevelRepository {
    private var lastLevel: Long
        get() {
            return sharedPreferences.getLong("LastLevel", 1)
        }
        set(value) {
            sharedPreferences.edit().putLong("LastLevel", value).commit()
        }

    override fun getCurrentLevelNumber(): Long {
        return lastLevel
    }

    override fun levelPassed(level: Level,isChange:Boolean) {

        lastLevel = if (isChange){
            val settingLevel = max(level.number, lastLevel)
            settingLevel
        } else{
            val settingLevel = max(level.number + 1, lastLevel)
            settingLevel
        }


    }

    override fun reset() {
        lastLevel = 1
    }
}