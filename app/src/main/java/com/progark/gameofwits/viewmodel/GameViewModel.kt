package com.progark.gameofwits.viewmodel


import androidx.lifecycle.*
import com.progark.gameofwits.model.Lobby
import kotlinx.coroutines.launch
import storage.Repository
import storage.Storage

class GameViewModel(private val repository: Repository = Storage.getInstance()): ViewModel() {

    val lobbies = MutableLiveData<List<Lobby>>()
    fun fetchLobbies() {
        viewModelScope.launch {
            lobbies.postValue(repository.getLobbies())
        }
    }

    fun getLobby() = liveData {
        emit(repository.getLobby("D82qYqJLp1oyjJ8EP3cC"))
    }

    fun getGame() = liveData {
        emit(repository.getGame("psfwZoWRJGEZXnoCcLOC"))
    }

}