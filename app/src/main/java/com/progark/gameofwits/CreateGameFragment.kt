package com.progark.gameofwits

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.progark.gameofwits.databinding.FragmentCreateGameBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CreateGameFragment : Fragment() {

    private var _binding: FragmentCreateGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
                createGamePIN()
                openLobbyView()}

        }

    }

    private fun createGamePIN():String {
        var pin = ""
        for (i in 1..5) pin+=(0..9).random().toString()
        return pin
    }

    private fun openLobbyView() {
        val intent = Intent(getActivity(), LobbyView::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}