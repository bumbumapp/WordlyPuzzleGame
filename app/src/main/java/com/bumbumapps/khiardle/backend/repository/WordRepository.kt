package com.bumbumapps.khiardle.backend.repository

import com.bumbumapps.khiardle.backend.models.Word

interface WordRepository {
    val lastLevel: Long
    fun find(word: Word): Boolean
    fun random(): Word
    fun getWordForLevel(currentLevelNumber: Long): Word
}
