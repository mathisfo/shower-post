package com.progark.gameofwits.viewmodel

import androidx.lifecycle.*
import com.progark.gameofwits.model.Lobby
import kotlinx.coroutines.launch
import storage.Repository
import storage.Storage

class LobbyViewModel(private val repository: Repository = Storage.getInstance()) : ViewModel() {

    private val _lobby = MutableLiveData<Lobby>()
    val lobby: LiveData<Lobby> = _lobby


    fun setActiveLobby(payload: Lobby) {
        _lobby.postValue(payload)
    }

    fun fetchLobby(id: String) {
        viewModelScope.launch {
            val lobby = repository.getLobby(id)
            _lobby.postValue(lobby)
        }
    }

    fun joinLobbyWithName(name: String, gamePIN: String) {
        viewModelScope.launch { repository.joinLobbyWithName(name, gamePIN) }
    }
}