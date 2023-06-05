package ru.chieffly.sumtrainer.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.chieffly.sumtrainer.R
import ru.chieffly.sumtrainer.domain.model.GameResult

/**
 *Created by Bryantsev Anton on 05.06.2023.
 **/

@BindingAdapter("result")
fun bindingRequiredAnswers(textView: TextView, winner: Boolean) {
    textView.text = if (winner)
        textView.context.getString(R.string.result_win)
    else
        textView.context.getString(R.string.result_loose)

}

@BindingAdapter("requiredAnswers")
fun bindingRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.require_score),
        count
    )
}

@BindingAdapter("requiredPercent")
fun bindingRequiredPercent(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.require_percent),
        count
    )
}

@BindingAdapter("score")
fun bindingScore(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.result_score),
        count
    )
}

@BindingAdapter("percent")
fun bindingPercent(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.result_percent),
        getPercentOfRightAnswers(gameResult)
    )
}

private fun getPercentOfRightAnswers(gameResult: GameResult) =
    with(gameResult) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

@BindingAdapter("resultPicture")
fun bindingResultPicture(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getPictureResId(winner))
}

private fun getPictureResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.ic_win
    } else {
        R.drawable.ic_loose
    }
}