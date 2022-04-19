package com.progark.gameofwits.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.progark.gameofwits.R
import com.progark.gameofwits.databinding.FragmentJoinGameBinding
import com.progark.gameofwits.viewmodel.JoinGameViewModel


class JoinGameFragment : Fragment() {

    private var _binding: FragmentJoinGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: JoinGameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJoinGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.joinGameViewModel = viewModel
        binding.lifecycleOwner = this
        binding.joinConfirmButton.setOnClickListener {
            viewModel.joinLobby()
        }
        binding.joinBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_JoinGameFragment_to_MainMenuFragment)
        }
        viewModel.activeLobbyId.observe(viewLifecycleOwner) { lobbyId ->
            val intent = Intent(requireContext(), GameActivity::class.java)
            println("lobby $lobbyId")
            intent.putExtra("ACTIVE_LOBBY_ID", lobbyId)
            intent.putExtra("USER_ID", viewModel.userId.value!!)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}