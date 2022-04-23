package com.progark.gameofwits.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.progark.gameofwits.model.Round
import kotlinx.coroutines.launch
import storage.Repository
import storage.Storage

class GameRoundViewModel(private val repository: Repository = Storage.getInstance()) : ViewModel() {
    private val _activeRound = MutableLiveData<Round>()
    val activeRound: LiveData<Round> = _activeRound

    private val _word = MutableLiveData<String>()
    val word: LiveData<String> = _word

    fun setRound(round: Round) {
        _activeRound.postValue(round)
    }

    fun addLetter(c: Char) {
        val word = _word.value ?: ""
        _word.postValue(word + c)
    }

    fun resetWord() {
        _word.postValue("")
    }
}