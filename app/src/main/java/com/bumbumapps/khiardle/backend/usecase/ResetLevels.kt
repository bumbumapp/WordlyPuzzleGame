package com.bumbumapps.khiardle.backend.usecase

import com.bumbumapps.khiardle.backend.repository.LevelRepository

class ResetLevels(
    private val levelRepository: LevelRepository,
) {
    fun execute() {
        levelRepository.reset()
    }
}