package ru.chieffly.sumtrainer.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.chieffly.sumtrainer.R
import ru.chieffly.sumtrainer.data.GameRepositoryImpl
import ru.chieffly.sumtrainer.domain.model.GameResult
import ru.chieffly.sumtrainer.domain.model.GameSettings
import ru.chieffly.sumtrainer.domain.model.Level
import ru.chieffly.sumtrainer.domain.model.Question
import ru.chieffly.sumtrainer.domain.usecases.GenerateQuestionUseCase
import ru.chieffly.sumtrainer.domain.usecases.GetGameSettingsUseCase

/**
 *Created by Bryantsev Anton on 01.06.2023.
 **/

class GameViewModel(private val application: Application,
                    private val level: Level) : ViewModel() {
    private lateinit var settings: GameSettings

    private var timer: CountDownTimer? = null
    private val repository = GameRepositoryImpl

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    init {
        startGame()
    }

    fun startGame() {
        getGameSettings()
        startTimer()
        getNextQuestion()
    }

    fun receiveAnswer(answer: Int) {
        checkAnswer(answer)
        updateProgress()
        getNextQuestion()
    }

    fun finishGame() {
        _gameResult.value = GameResult(
            winner = enoughCountOfRightAnswers.value == true && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestions = countOfQuestions,
            gameSettings = settings
        )
    }

    private fun getGameSettings() {
        settings = getGameSettingsUseCase(level)
        _minPercent.value = settings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(settings.gameTimeInSeconds * MILLIS_IN_SECONDS, MILLIS_IN_SECONDS) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    fun getNextQuestion() {
        _question.value = generateQuestionUseCase(settings.maxSumValue)
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MIN
        val leftSeconds = seconds - (minutes * SECONDS_IN_MIN)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun checkAnswer(answer: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (answer == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(application.resources.getString(R.string.right_answers), countOfRightAnswers, settings.minCountOfRightAnswers)
        _enoughCountOfRightAnswers.value = countOfRightAnswers >= settings.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value = percent >= settings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * PERCENT).toInt()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MIN = 60
        private const val PERCENT = 100
    }

}