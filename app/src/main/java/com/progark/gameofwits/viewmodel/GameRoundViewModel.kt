package com.progark.gameofwits.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameRoundViewModel : ViewModel() {
    private val _currentRound = MutableLiveData<Int>()
    val currentRound: LiveData<Int> = _currentRound

    private val _word = MutableLiveData<String>()
    val word: LiveData<String> = _word

    fun addLetter(c: Char) {
        val word = _word.value ?: ""
        _word.postValue(word + c)
    }
}