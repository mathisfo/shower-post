package com.progark.gameofwits.viewmodel

import androidx.lifecycle.*
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.observers.Observer
import com.progark.gameofwits.observers.PlayerEventSource
import kotlinx.coroutines.launch
import model.User
import storage.Repository
import storage.Storage

class LobbyViewModel(private val repository: Repository = Storage.getInstance()) : ViewModel(),
    Observer {

    private val _lobby = MutableLiveData<Lobby>()
    val lobby: LiveData<Lobby> = _lobby

    fun fetchLobby(id: String) {
        viewModelScope.launch {
            val lobby = repository.getLobby(id)
            _lobby.postValue(lobby)
            repository.listenToLobby(id)
        }
    }

    override fun update(event: String, payload: Any?) {
        if (event == "PLAYER_JOINED") {
            val lobby = _lobby.value!!
            val user = payload as User
            if (user !in lobby.players) lobby.players.add(payload)
            _lobby.postValue(lobby)
        }
    }
}