package com.progark.gameofwits.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.progark.gameofwits.R
import com.progark.gameofwits.databinding.FragmentJoinGameBinding
import com.progark.gameofwits.viewmodel.MainMenuViewModel


class JoinGameFragment : Fragment() {

    private var _binding: FragmentJoinGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val activityViewModel: MainMenuViewModel by activityViewModels()

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
        binding.joinBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_JoinGameFragment_to_MainMenuFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}