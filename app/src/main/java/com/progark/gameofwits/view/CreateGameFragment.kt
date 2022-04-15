package com.progark.gameofwits.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.progark.gameofwits.R
import com.progark.gameofwits.databinding.FragmentCreateGameBinding
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.viewmodel.LobbyViewModel
import com.progark.gameofwits.model.Player

/**
 * A simple [Fragment] subclass for the "Create Game" view.
 */
class CreateGameFragment : Fragment() {
    private var _binding: FragmentCreateGameBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var gamePin = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.hostBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_CreateGameFragment_to_MainMenuFragment)
        }
        binding.hostConfirmButton.setOnClickListener{
            val userName = binding.hostNameField.text.toString()
            if (userName.isEmpty()){
                Toast.makeText(context,"You did not enter a userName",Toast.LENGTH_SHORT).show()
            } else {
                val lobby = Lobby("", createGamePIN(), true, 0.0, userName, mutableListOf(Player("",userName, false)))
                openLobbyView(lobby)}
        }
    }

    private fun createGamePIN():String {
        for (i in 1..4) gamePin+=(0..9).random().toString()
        println("created pin: " + gamePin)
        return gamePin
    }

    /**
    fun getGamePIN():String{
        println("PIN in getGamePIN: " + gamePin)
        return gamePin;
    }
    **/

    private fun openLobbyView(lobby: Lobby) {
        val gameViewModel: LobbyViewModel by viewModels()
        val intent = Intent(getContext(), LobbyView::class.java)
        val docRef = gameViewModel.createLobbyAndAddToStore(lobby).observe(viewLifecycleOwner) {lobbyRef ->
            intent.putExtra("LOBBY_REFERENCE", lobbyRef)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}