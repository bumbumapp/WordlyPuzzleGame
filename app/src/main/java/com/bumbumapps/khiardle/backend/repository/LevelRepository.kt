package com.bumbumapps.khiardle.backend.repository

import com.bumbumapps.khiardle.backend.models.Level

interface LevelRepository {
    fun getCurrentLevelNumber(): Long
    fun levelPassed(level: Level,isChange:Boolean)
    fun reset()
}