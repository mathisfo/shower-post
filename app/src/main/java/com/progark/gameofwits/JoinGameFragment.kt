package com.progark.gameofwits

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.progark.gameofwits.databinding.FragmentJoinGameBinding


class JoinGameFragment : Fragment() {

    private var _binding: FragmentJoinGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        println("TEST ME")
        binding.joinBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_JoinGameFragment_to_FirstFragment)
        }
        binding.joinConfirmButton.setOnClickListener{
            val userName = binding.joinNameField.text.toString()
            val gameCode = binding.joinCodeField.text.toString()
            val testCode = "123"
            if (userName.isEmpty()){
                Toast.makeText(context,"You did not enter a userName", Toast.LENGTH_SHORT).show()
            }
            if (!gameCode.equals(testCode))
                Toast.makeText(context, "Invalid game code", Toast.LENGTH_SHORT).show()
            else openLobbyView()
        }
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