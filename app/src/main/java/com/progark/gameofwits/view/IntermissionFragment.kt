package com.progark.gameofwits.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
        val submissions: TextView = binding.submissions
        nextword.setOnClickListener {}
        gameViewModel.submittedWords.observe(viewLifecycleOwner) { submitted ->
            submissions.text = "" + submitted + "/" + gameViewModel.activeLobby.value!!.players.size + "players ready"
            if (submitted == gameViewModel.activeLobby.value!!.players.size) {
                nextword.visibility = View.VISIBLE
            }
        }
    }

}