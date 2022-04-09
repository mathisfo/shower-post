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

    var activeLobbyId = MutableLiveData("")

    fun getLobby() = liveData {
        emit(repository.getLobby(activeLobbyId.value!!))
    }


    fun createLobbyAndAddToStore(lobby: Lobby) {
        viewModelScope.launch {
            val id: String = repository.createLobbyAndAddToStore(lobby)
            activeLobbyId.postValue(id)

        }
    }
}