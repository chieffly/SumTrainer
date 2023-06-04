package ru.chieffly.sumtrainer.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *Created by Bryantsev Anton on 31.05.2023.
 **/

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
) : Parcelable