package com.progark.gameofwits.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.progark.gameofwits.MainActivity
import com.progark.gameofwits.R
import com.progark.gameofwits.databinding.FragmentCreateLobbyBinding
import com.progark.gameofwits.databinding.FragmentEndofgameBinding
import com.progark.gameofwits.viewmodel.CreateLobbyViewModel
import com.progark.gameofwits.viewmodel.GameViewModel

class EndOfGameFragment : Fragment() {
    private var _binding: FragmentEndofgameBinding? = null
    private val binding get() = _binding!!
    val gameViewModel : GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEndofgameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val playagain = binding.playagainbtn
        val mainmenu = binding.mainmenubtn

        playagain.setOnClickListener {
            findNavController().navigate(R.id.action_endOfGameFragment_to_lobbyFragment)
        }
        mainmenu.setOnClickListener {}
    }
}