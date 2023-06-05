package ru.chieffly.sumtrainer.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
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

@BindingAdapter("percentOfRightAnswers")
fun bindingPercentOfRightAnswers(progressBar: ProgressBar, progress: Int) {
    progressBar.setProgress(progress, true)
}

@BindingAdapter("numberAsText")
fun bindingNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("enoughCount")
fun bindingEnoughCount(textView: TextView, enough: Boolean) {
    val color = getColorByState(textView.context, enough)
    textView.setTextColor(color)
}

@BindingAdapter("enoughPercent")
fun bindingEnoughPercent(progressBar: ProgressBar, enough: Boolean) {
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_dark
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("onOptionClickListener")
fun bindingOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}

interface OnOptionClickListener {
    fun onOptionClick (int: Int)
}