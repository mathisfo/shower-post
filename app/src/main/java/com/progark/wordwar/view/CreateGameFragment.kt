package com.progark.wordwar.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.progark.wordwar.R
import com.progark.wordwar.databinding.FragmentCreateLobbyBinding
import com.progark.wordwar.viewmodel.CreateLobbyViewModel

/**
 * A simple [Fragment] subclass for the "Create Game" view.
 */
class CreateGameFragment : Fragment() {
    private var _binding: FragmentCreateLobbyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: CreateLobbyViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Init
        super.onViewCreated(view, savedInstanceState)
        binding.createLobbyViewModel = viewModel
        binding.lifecycleOwner = this

        binding.hostBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_CreateGameFragment_to_MainMenuFragment)
        }
        binding.hostConfirmButton.setOnClickListener {
            val userName = binding.hostNameField.text.toString()
            if (userName.isEmpty()) {
                Toast.makeText(context, "You did not enter a username", Toast.LENGTH_SHORT).show()
            }
            viewModel.createLobby()
        }
        viewModel.lobbyId.observe(viewLifecycleOwner) { lobbyId ->
            openLobbyView(lobbyId, viewModel.userId.value!!)
        }
    }

    private fun openLobbyView(lobbyId: String, userId: String) {
        val intent = Intent(requireContext(), GameActivity::class.java)
        intent.putExtra("ACTIVE_LOBBY_ID", lobbyId)
        intent.putExtra("USER_ID", userId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}