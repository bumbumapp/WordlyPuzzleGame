package com.bumbumapps.khiardle.backend.viewmodel

import com.bumbumapps.khiardle.backend.models.Level
import com.bumbumapps.khiardle.backend.repository.LevelRepository
import com.bumbumapps.khiardle.backend.usecase.GetNextLevel
import com.bumbumapps.khiardle.backend.usecase.ResetLevels

class LevelsViewModel(
    private val levelRepository: LevelRepository,
    private val getNextLevel: GetNextLevel,
    private val resetLevels: ResetLevels,
) : BaseViewModel<LevelsViewModel.State>(State()) {
    data class State(
        val currentLevel: Level? = null,
        val lastLevelReached: Boolean = false,

        )

    init {
        updateLevel()
    }

    fun levelPassed(ischange:Boolean) {
        currentState().currentLevel?.let { levelRepository.levelPassed(it,ischange) }
        updateLevel()
    }


    private fun updateLevel() {
        val nextLevel = getNextLevel.execute()
        if (nextLevel == null) {
            updateState { copy(lastLevelReached = true, currentLevel = null) }
            return
        }
        updateState {
            copy(currentLevel = nextLevel, lastLevelReached = false)
        }
    }

    fun reset() {
        resetLevels.execute()
        updateLevel()
    }
}