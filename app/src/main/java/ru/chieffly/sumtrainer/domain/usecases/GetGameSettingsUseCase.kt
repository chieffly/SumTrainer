package ru.chieffly.sumtrainer.domain.usecases

import ru.chieffly.sumtrainer.domain.model.GameSettings
import ru.chieffly.sumtrainer.domain.model.Level
import ru.chieffly.sumtrainer.domain.repository.GameRepository

/**
 *Created by Bryantsev Anton on 31.05.2023.
 **/

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}