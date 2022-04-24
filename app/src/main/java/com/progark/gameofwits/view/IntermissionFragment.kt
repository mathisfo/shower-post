package com.progark.gameofwits.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.progark.gameofwits.R
import com.progark.gameofwits.databinding.FragmentIntermissionBinding
import com.progark.gameofwits.viewmodel.GameViewModel

class IntermissionFragment : Fragment() {
    val gameViewModel : GameViewModel by activityViewModels()

    private var _binding: FragmentIntermissionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val nextword: Button = binding.nextword
        val endGame: Button = binding.endgame
        var currentRound = gameViewModel.game.value!!.current_round
        val submissions: TextView = binding.submissions
        val roundCounter = binding.roundcounter
        nextword.setOnClickListener {
            gameViewModel.updateCurrentRound()
        }
        gameViewModel.submittedWords.observe(viewLifecycleOwner) { submitted ->
            submissions.text = "" + submitted + "/" + gameViewModel.activeLobby.value!!.players.size + " players ready"
            val lobby = gameViewModel.activeLobby.value!!
            val game = gameViewModel.game.value!!
            roundCounter.text = "${game.current_round}/${game.max_round}"
            if (submitted == lobby.players.size &&
                lobby.isHost(gameViewModel.user.value!!)) {
                    if (game.current_round == game.max_round) {
                        endGame.visibility = View.VISIBLE
                    }
                    else {
                        nextword.visibility = View.VISIBLE
                    }
            }
        }
        gameViewModel.game.observe(viewLifecycleOwner) { game ->
            if (game.current_round > currentRound) {
                currentRound += 1
                nextword.visibility = View.INVISIBLE
                openGameRoundView()
            }
        }
        endGame.setOnClickListener {
            gameViewModel.endCurrentGame()
        }
        gameViewModel.ended.observe(viewLifecycleOwner) { ended ->
            if (ended) {
                findNavController().navigate(R.id.action_intermissionFragment_to_endOfGameFragment)
            }
        }
    }

    private fun openGameRoundView() {
        findNavController().navigate(R.id.action_intermissionFragment_to_gameRoundFragment)
    }
}