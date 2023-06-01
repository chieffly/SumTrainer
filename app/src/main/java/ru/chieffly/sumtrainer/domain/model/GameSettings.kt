package ru.chieffly.sumtrainer.domain.model

import java.io.Serializable

/**
 *Created by Bryantsev Anton on 31.05.2023.
 **/

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
) : Serializable