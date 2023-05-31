package ru.chieffly.sumtrainer.domain.model

/**
 *Created by Bryantsev Anton on 31.05.2023.
 **/

data class Question(
    val sum: Int,
    val visibleNumver: Int,
    val option: List<Int>
)