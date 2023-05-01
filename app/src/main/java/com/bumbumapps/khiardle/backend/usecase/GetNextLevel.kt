package com.bumbumapps.khiardle.backend.usecase

import com.bumbumapps.khiardle.backend.models.Level
import com.bumbumapps.khiardle.backend.repository.LevelRepository
import com.bumbumapps.khiardle.backend.repository.WordRepository

class GetNextLevel(
    private val wordRepository: WordRepository,
    private val levelRepository: LevelRepository,
) {
    fun execute(): Level? {
        val currentLevelNumber = levelRepository.getCurrentLevelNumber()
        if (currentLevelNumber >= wordRepository.lastLevel + 1) return null
        return wordRepository.getWordForLevel(currentLevelNumber).let { word ->
            Level(currentLevelNumber, word)
        }
    }
}