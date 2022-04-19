package com.progark.gameofwits.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.progark.gameofwits.model.Lobby
import kotlinx.coroutines.launch
import storage.Repository
import storage.Storage

class CreateGameViewModel(private val repository: Repository = Storage.getInstance()) :
    ViewModel() {
    // Input fields
    val usernameInput = ObservableField<String>()
    private val _lobbyId = MutableLiveData<String>()
    val lobbyId: LiveData<String> = _lobbyId

    fun createLobby() {
        val pin = createRandomPin()
        val lobbyData = Lobby("", pin, true)
        viewModelScope.launch {
            val hostId = repository.createUser(usernameInput.get()!!)
            val lobbyId = repository.createLobby(lobbyData, hostId)
            _lobbyId.postValue(lobbyId)
            repository.openLobby(lobbyId, pin)
            repository.joinLobbyWithPin(hostId, usernameInput.get()!!, pin)
        }
    }

    private fun createRandomPin(): String {
        var pin = ""
        repeat(5) {
            val randomNum = (0..9).shuffled().last()
            pin += "$randomNum"
        }
        return pin
    }
}
