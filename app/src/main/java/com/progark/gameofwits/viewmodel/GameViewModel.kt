package com.progark.gameofwits.viewmodel


import androidx.lifecycle.*
import com.progark.gameofwits.model.*
import com.progark.gameofwits.observers.PlayerObserver
import kotlinx.coroutines.launch
import model.User
import storage.Repository
import storage.Storage

class GameViewModel(private val repository: Repository = Storage.getInstance()): ViewModel(), PlayerObserver {
    private val _activeLobby = MutableLiveData<Lobby>()
    val activeLobby: LiveData<Lobby> = _activeLobby

    private val _currentGame = MutableLiveData<Game>()

    private val _user = MutableLiveData<String>()
    val user : LiveData<String> = _user



    fun fetchLobby(id: String) {
        viewModelScope.launch {
            val lobby = repository.getLobby(id)
            _activeLobby.postValue(lobby)
            repository.listenToLobby(id)
        }
    }

    fun setActiveUser(id: String) {
        _user.postValue(id)
    }

    override fun update(event: String, payload: User?) {
        if (event == "PLAYER_JOINED") {
            val lobby = _activeLobby.value!!
            val user = payload as User
            lobby.join(user)
            _activeLobby.postValue(lobby)
        }
    }

    fun createGame() {
        viewModelScope.launch {
            repository.createGame(activeLobby.value!!, 5)
        }
    }
}