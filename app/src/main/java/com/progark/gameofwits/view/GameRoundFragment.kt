package com.progark.gameofwits.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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


        val endofgame = binding.endofgamebtn
        endofgame.setOnClickListener { }

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
    }

    /*
    private fun openIntermissionView() {
        val intent = Intent(this, IntermissionView::class.java)
        startActivity(intent)
    }

    private fun openEndOfGameView() {
        val intent = Intent(this, EndOfGameView::class.java)
        startActivity(intent)
    }
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}