package ru.chieffly.sumtrainer.presentation

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import ru.chieffly.sumtrainer.R
import ru.chieffly.sumtrainer.databinding.FragmentResultBinding
import ru.chieffly.sumtrainer.domain.model.GameResult


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding
        get() = _binding ?: throw RuntimeException("FragmentResultBinding == null")

    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        })
    }

    private fun bindViews() {
        with(binding) {
            tvScore.text = String.format(
                getString(R.string.result_score),
                gameResult.countOfRightAnswers.toString()
            )
            tvScoreReq.text = String.format(
                getString(R.string.require_score),
                gameResult.gameSettings.minCountOfRightAnswers.toString()
            )
            tvPercent.text = String.format(
                getString(R.string.result_percent),
                getPercentOfRightAnswers().toString()
            )
            tvPercentReq.text = String.format(
                getString(R.string.require_percent),
                gameResult.gameSettings.minPercentOfRightAnswers.toString()
            )
        }
    }

    private fun getPercentOfRightAnswers() =
        with(gameResult) {
            if (countOfQuestions == 0) {
                0
            } else {
                ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble()) * 100).toInt()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        gameResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(KEY_GAME_RESULT, GameResult::class.java)!!
        } else {
            requireArguments().getParcelable(KEY_GAME_RESULT)!!
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"
        fun newInstance(results: GameResult): ResultFragment {
            return ResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, results)
                }
            }
        }
    }
}