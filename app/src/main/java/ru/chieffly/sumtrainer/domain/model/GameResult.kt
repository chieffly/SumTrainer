package ru.chieffly.sumtrainer.domain.model

/**
 *Created by Bryantsev Anton on 31.05.2023.
 **/

class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestins: Int,
    val gameSettings: GameSettings
)