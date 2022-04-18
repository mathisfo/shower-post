package com.progark.gameofwits.viewmodel

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
            val userId = repository.getUserId()
            _userId.postValue(userId)
            val lobbyId = repository.joinLobbyWithPin("2bH4Kmrz9eE2KyTYVQhb", username, pin)
            _activeLobbyId.postValue(lobbyId)
        }
    }
}