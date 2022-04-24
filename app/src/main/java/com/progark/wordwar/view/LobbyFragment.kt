package com.progark.wordwar.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.progark.wordwar.R
import com.progark.wordwar.model.Player
import com.progark.wordwar.viewmodel.GameViewModel
import com.progark.wordwar.databinding.FragmentLobbyBinding
import com.progark.wordwar.observers.PlayerEventSource
import com.progark.wordwar.view.adapters.PlayerAdapter
import model.User

class LobbyFragment() : Fragment() {

    private var _binding: FragmentLobbyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val gameViewModel: GameViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = binding.lobbyId
        val btn = binding.nextbutton
        val waitingText = binding.waitingText
        btn.setOnClickListener {
            gameViewModel.createGame()
        }


        val players = mutableListOf<User>()
        val playersList = binding.playerList
        val adapter = PlayerAdapter(requireContext(), players)
        playersList.adapter = adapter

        gameViewModel.activeLobby.observe(viewLifecycleOwner) { lobby ->
            text.text = lobby.pin
            if (lobby.isHost(gameViewModel.user.value!!)) {
                btn.visibility = View.VISIBLE
                waitingText.visibility = View.INVISIBLE
            }
            lobby.players.forEach { player ->
                if (!players.contains(player)) players.add(player)
            }
            adapter.notifyDataSetChanged()
        }
        gameViewModel.game.observe(viewLifecycleOwner) {game ->
            if (game != null) {
                findNavController().navigate(R.id.action_lobbyFragment_to_gameFragment)
            }
        }


    }


}