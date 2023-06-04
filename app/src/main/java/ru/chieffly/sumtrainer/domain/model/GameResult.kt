package ru.chieffly.sumtrainer.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *Created by Bryantsev Anton on 31.05.2023.
 **/

@Parcelize
class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
) : Parcelable