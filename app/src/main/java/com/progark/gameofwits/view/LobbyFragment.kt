package com.progark.gameofwits.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.progark.gameofwits.MainActivity
import com.progark.gameofwits.R
import com.progark.gameofwits.databinding.FragmentLobbyBinding
import com.progark.gameofwits.view.adapters.PlayerAdapter
import com.progark.gameofwits.viewmodel.GameViewModel
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
        var rounds = 3
        btn.setOnClickListener {
            gameViewModel.createGame(rounds)
        }
        val numberOfRounds = binding.numberOfRounds
        numberOfRounds.setOnCheckedChangeListener {group, checkedId ->
            println("Checked: " + checkedId)
            when (checkedId) {
                R.id.btn2 -> {
                    println("Rounds: " + 1)
                    rounds = 2
                }
                R.id.btn3 -> {
                    println("Rounds: " + 1)
                    rounds = 3
                }
                R.id.btn4 -> {
                    println("Rounds: " + 1)
                    rounds = 4
                }
                R.id.btn5 -> {
                    println("Rounds: " + 1)
                    rounds = 5
                }
            }
        }


        val players = mutableListOf<User>()
        val playersList = binding.playerList
        val adapter = PlayerAdapter(requireContext(), players)
        playersList.adapter = adapter
        val roundsText = binding.roundsText

        gameViewModel.activeLobby.observe(viewLifecycleOwner) { lobby ->
            text.text = lobby.pin
            if (lobby.isHost(gameViewModel.user.value!!)) {
                btn.visibility = View.VISIBLE
                waitingText.visibility = View.INVISIBLE
                numberOfRounds.visibility = View.VISIBLE
                roundsText.visibility = View.VISIBLE
            }
            lobby.players.forEach { player ->
                if (!players.contains(player)) players.add(player)
            }
            if (!lobby.active) {
                Toast.makeText(requireContext(), "The host has terminated the lobby", Toast.LENGTH_SHORT).show()
                openMainMenuView()
            }
            adapter.notifyDataSetChanged()
        }
        gameViewModel.game.observe(viewLifecycleOwner) {game ->
            if (game != null && !game.ended) {
                findNavController().navigate(R.id.action_lobbyFragment_to_gameFragment)
            }
        }


    }
    private fun openMainMenuView() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }
}