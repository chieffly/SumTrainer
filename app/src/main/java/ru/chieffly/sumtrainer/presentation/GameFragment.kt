package ru.chieffly.sumtrainer.presentation

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.chieffly.sumtrainer.R
import ru.chieffly.sumtrainer.databinding.FragmentGameBinding
import ru.chieffly.sumtrainer.domain.model.GameResult
import ru.chieffly.sumtrainer.domain.model.Level

class GameFragment : Fragment() {
    private lateinit var level: Level

    private val args by navArgs<GameFragmentArgs>()

    private val factory: GameViewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, factory)[GameViewModel::class.java]
    }

    private val optionList by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOptions1)
            add(binding.tvOptions2)
            add(binding.tvOptions3)
            add(binding.tvOptions4)
            add(binding.tvOptions5)
            add(binding.tvOptions6)
        }
    }
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        observeViewModel()
    }

    private fun setClickListeners() {
        for (options in optionList) {
            options.setOnClickListener {
                viewModel.receiveAnswer(options.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            with(binding) {
                tvGameSum.text = it.sum.toString()
                tvGameLeftNumber.text = it.visibleNumber.toString()
                for (i in 0 until optionList.size) {
                    optionList[i].text = it.option[i].toString()
                }
            }
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvGameResult.text = it
        }

        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }

        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvGameTimer.text = it
        }

        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.tvGameResult.setTextColor(color)

        }
        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }

        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameResultFragment(it)
        }

        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_dark
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameResultFragment(gameResult: GameResult) {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToResultFragment(gameResult))
    }
}