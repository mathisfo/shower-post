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
import com.progark.gameofwits.databinding.FragmentJoinGameBinding
import com.progark.gameofwits.viewmodel.MainMenuViewModel
import com.progark.gameofwits.viewmodel.LobbyViewModel


class JoinGameFragment : Fragment() {

    private var _binding: FragmentJoinGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MainMenuViewModel by viewModels()

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

        // These must be set
        binding.mainMenuViewModel =  viewModel
        binding.lifecycleOwner = this

        binding.joinBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_JoinGameFragment_to_MainMenuFragment)
        }
        viewModel.validOrError.observe(viewLifecycleOwner) {(valid, error) ->
            if (!valid) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            } else {
                openLobbyView()
            }
        }
    }

    private fun openLobbyView() {
        val intent = Intent(getActivity(), LobbyView::class.java)
        val lobbyViewModel: LobbyViewModel by viewModels()
        lobbyViewModel.joinLobbyWithName("Johanne", "2180")
        startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}