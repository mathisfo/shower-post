package com.progark.gameofwits.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.progark.gameofwits.MainActivity
import com.progark.gameofwits.R
import com.progark.gameofwits.databinding.FragmentEndofgameBinding
import com.progark.gameofwits.viewmodel.GameViewModel

class EndOfGameFragment : Fragment() {
    private var _binding: FragmentEndofgameBinding? = null
    private val binding get() = _binding!!
    private val gameViewModel: GameViewModel by activityViewModels()

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

        val listview = binding.scores
        val items: MutableList<String> = mutableListOf()
        val adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, items)
        listview.adapter = adapter


        gameViewModel.game.observe(viewLifecycleOwner) { game ->
            val players = gameViewModel.activeLobby.value!!.players
            game.scores.toList().sortedBy { (_, value) -> -value}.toMap().entries.forEach { (key, value) ->
                val user = players.find { user -> user.id == key }
                if (user != null) {
                    val text = "${user.name}: $value"
                    if (!items.contains(text)) items.add(text)
                }
            }
            adapter.notifyDataSetChanged()
        }

        gameViewModel.activeLobby.observe(viewLifecycleOwner) { lobby ->
               if (!lobby.active) {
                   openMainMenuView()
               }
        }

        playagain.setOnClickListener {
            findNavController().navigate(R.id.action_endOfGameFragment_to_lobbyFragment)
        }
        mainmenu.setOnClickListener {
            if (gameViewModel.activeLobby.value!!.isHost(gameViewModel.user.value!!)) {
                gameViewModel.mainMenu()
            }
            else openMainMenuView()
        }


    }

    private fun openMainMenuView() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }
}