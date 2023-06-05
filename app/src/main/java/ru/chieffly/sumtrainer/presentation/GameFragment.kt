package ru.chieffly.sumtrainer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.chieffly.sumtrainer.databinding.FragmentGameBinding
import ru.chieffly.sumtrainer.domain.model.GameResult

class GameFragment : Fragment() {
    private val args by navArgs<GameFragmentArgs>()

    private val factory: GameViewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, factory)[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameResultFragment(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameResultFragment(gameResult: GameResult) {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToResultFragment(gameResult))
    }
}