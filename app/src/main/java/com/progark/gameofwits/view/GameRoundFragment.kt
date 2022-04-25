package com.progark.gameofwits.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.progark.gameofwits.R
import com.progark.gameofwits.databinding.FragmentGameRoundBinding
import com.progark.gameofwits.view.adapters.LetterAdapter
import com.progark.gameofwits.view.adapters.LetterItem
import com.progark.gameofwits.viewmodel.GameRoundViewModel
import com.progark.gameofwits.viewmodel.GameViewModel

class GameRoundFragment : Fragment() {
    private val gameViewModel: GameViewModel by activityViewModels()
    private val roundViewModel: GameRoundViewModel by viewModels()

    private var _binding: FragmentGameRoundBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameRoundBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val game = gameViewModel.game.value!!
        val currentRound = game.rounds[game.current_round - 1]
        roundViewModel.setRound(currentRound)
        val currentRoundText = binding.roundCounterGame
        currentRoundText.text = "Playing round ${game.current_round}/${game.max_round} "


        val skipWord = binding.skip
        skipWord.setOnClickListener {
            gameViewModel.submitWord(" ")
        }

        val word = currentRound.letters
        val letters = word.toCharArray().map { c -> LetterItem(c, false) }
        val adapter = LetterAdapter(requireContext(), letters)
        adapter.setButtonOnclick { buttonView, position, letter ->
            letters[position].clicked = true
            roundViewModel.addLetter(letter)
            adapter.notifyDataSetChanged()
        }
        roundViewModel.word.observe(viewLifecycleOwner) { updatedWord ->
            binding.word.text = updatedWord
        }

        val lettersView = binding.availableLetters
        lettersView.adapter = adapter

        val resetbtn = binding.reset
        resetbtn.setOnClickListener {
            roundViewModel.resetWord()
            letters.forEach { item -> item.clicked = false }
            adapter.notifyDataSetChanged()
        }

        val enterword = binding.enterword
        enterword.setOnClickListener {
            gameViewModel.submitWord(roundViewModel.word.value!!)
        }
        gameViewModel.validOrError.observe(viewLifecycleOwner) { validOrError ->
            val valid = validOrError.first
            val error = validOrError.second
            if (valid && error == "") {
                openIntermissionView()
            } else if (error != "") {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openIntermissionView() {
        findNavController().navigate(R.id.action_gameRoundFragment_to_intermissionFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}