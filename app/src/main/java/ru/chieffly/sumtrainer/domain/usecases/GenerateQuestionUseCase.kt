package ru.chieffly.sumtrainer.domain.usecases

import ru.chieffly.sumtrainer.domain.model.Question
import ru.chieffly.sumtrainer.domain.repository.GameRepository

/**
 *Created by Bryantsev Anton on 31.05.2023.
 **/

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}