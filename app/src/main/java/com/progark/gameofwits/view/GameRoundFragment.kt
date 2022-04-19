package com.progark.gameofwits.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.progark.gameofwits.databinding.FragmentGameRoundBinding
import com.progark.gameofwits.view.adapters.LetterAdapter
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
        val resetbtn = binding.reset

        val enterword = binding.enterword

        val endofgame = binding.endofgamebtn
        endofgame.setOnClickListener {  }

        val letters: List<Char> = listOf('a','b','c','d','e','e','f')
        val adapter = LetterAdapter(requireContext(), letters)
        adapter.setButtonOnclick { buttonView, position, letter ->
            buttonView.isEnabled = false
            buttonView.isClickable = false
            roundViewModel.addLetter(letter)
        }
        roundViewModel.word.observe(viewLifecycleOwner) { word ->
            binding.word.text = word
        }

        val lettersView = binding.availableLetters
        lettersView.adapter = adapter

        val writtenWord = binding.word
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