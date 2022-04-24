package com.progark.wordwar.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import storage.Repository
import storage.Storage

class JoinGameViewModel(private val repository: Repository = Storage.getInstance()) : ViewModel() {
    val pinField = ObservableField<String>()
    val nameField = ObservableField<String>()
    private val _activeLobbyId = MutableLiveData<String>()
    val activeLobbyId: LiveData<String> = _activeLobbyId

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId


    fun joinLobby() {
        val pin = pinField.get()!!
        val username = nameField.get()!!
        viewModelScope.launch {
            val userId = repository.createUser()
            _userId.postValue(userId)
            val lobbyId = repository.joinLobbyWithPin(userId, username, pin)
            _activeLobbyId.postValue(lobbyId)
        }
    }
}