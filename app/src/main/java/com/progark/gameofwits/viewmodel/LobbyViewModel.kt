package com.progark.gameofwits.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.progark.gameofwits.model.Lobby
import kotlinx.coroutines.launch
import storage.Repository
import storage.Storage

class LobbyViewModel(private val repository: Repository = Storage.getInstance()): ViewModel() {

    var activeLobbyId: String = "";


    fun createLobbyAndAddToStore(lobby: Lobby): String {
        viewModelScope.launch {
            val id = repository.createLobbyAndAddToStore(lobby)
            activeLobbyId = id;
        }
        return activeLobbyId;
    }

    fun getLobbyByPIN(pin: String) = liveData {
        emit(repository.getLobbyByPIN(pin))
    }

    fun getLobby(Id: String) = liveData {
        emit(repository.getLobby(Id))
    }

    fun joinLobbyWithName(name: String, gamePIN: String) {
        viewModelScope.launch { repository.joinLobbyWithName(name, gamePIN) }
    }
}