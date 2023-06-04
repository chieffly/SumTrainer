package ru.chieffly.sumtrainer.presentation

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.chieffly.sumtrainer.R
import ru.chieffly.sumtrainer.databinding.FragmentResultBinding
import ru.chieffly.sumtrainer.domain.model.GameResult


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding
        get() = _binding ?: throw RuntimeException("FragmentResultBinding == null")

    private val args by navArgs<ResultFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnUnderstand.setOnClickListener {
            retryGame()
        }
    }

    private fun bindViews() {
        with(binding) {
            tvScore.text = String.format(
                getString(R.string.result_score),
                args.gameResult.countOfRightAnswers.toString()
            )
            tvScoreReq.text = String.format(
                getString(R.string.require_score),
                args.gameResult.gameSettings.minCountOfRightAnswers.toString()
            )
            tvPercent.text = String.format(
                getString(R.string.result_percent),
                getPercentOfRightAnswers().toString()
            )
            tvPercentReq.text = String.format(
                getString(R.string.require_percent),
                args.gameResult.gameSettings.minPercentOfRightAnswers.toString()
            )
        }
    }

    private fun getPercentOfRightAnswers() =
        with(args.gameResult) {
            if (countOfQuestions == 0) {
                0
            } else {
                ((args.gameResult.countOfRightAnswers / args.gameResult.countOfQuestions.toDouble()) * 100).toInt()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun retryGame() {
        findNavController().popBackStack()
    }
}