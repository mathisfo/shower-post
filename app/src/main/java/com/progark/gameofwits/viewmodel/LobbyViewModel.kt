package com.progark.gameofwits.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.progark.gameofwits.model.Lobby
import storage.Repository
import storage.Storage

class LobbyViewModel(private val repository: Repository = Storage.getInstance()): ViewModel() {

    fun getLobby(id: String) = liveData {
        emit(repository.getLobby(id))
    }

    fun createLobbyAndAddToStore(lobby: Lobby) = liveData {
      emit( repository.createLobbyAndAddToStore(lobby))

    }
}