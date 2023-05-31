package ru.chieffly.sumtrainer.domain.repository

import ru.chieffly.sumtrainer.domain.model.GameSettings
import ru.chieffly.sumtrainer.domain.model.Level
import ru.chieffly.sumtrainer.domain.model.Question

/**
 *Created by Bryantsev Anton on 31.05.2023.
 **/

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}