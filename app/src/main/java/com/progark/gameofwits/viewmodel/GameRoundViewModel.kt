package com.progark.gameofwits.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.progark.gameofwits.model.Round

class GameRoundViewModel : ViewModel() {
    private val _activeRound = MutableLiveData<Round>()
    val activeRound: LiveData<Round> = _activeRound

    fun setRound(round: Round) {
        _activeRound.postValue(round)
    }

    private val _word = MutableLiveData<String>()
    val word: LiveData<String> = _word

    fun addLetter(c: Char) {
        val word = _word.value ?: ""
        _word.postValue(word + c)
    }

    fun resetWord() {
        _word.postValue("")
    }
}