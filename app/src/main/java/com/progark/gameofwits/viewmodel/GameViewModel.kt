package com.progark.gameofwits.viewmodel


import androidx.lifecycle.*
import com.progark.gameofwits.model.*
import com.progark.gameofwits.observers.PlayerObserver
import kotlinx.coroutines.launch
import model.User
import storage.Repository
import storage.Storage

class GameViewModel(private val repository: Repository = Storage.getInstance()) : ViewModel(),
    PlayerObserver {
    private val _activeLobby = MutableLiveData<Lobby>()
    val activeLobby: LiveData<Lobby> = _activeLobby


    private val _game = MutableLiveData<Game>()
    val game: LiveData<Game> = _game

    private val _user = MutableLiveData<String>()
    val user: LiveData<String> = _user


    fun fetchLobby(id: String) {
        viewModelScope.launch {
            val lobby = repository.getLobby(id)
            _activeLobby.postValue(lobby)
            repository.listenToLobby(id)
            repository.listenOnLobbyForGames(id)
        }
    }

    fun setActiveUser(id: String) {
        _user.postValue(id)
    }

    override fun update(event: String, payload: Any?) {
        if (event == "PLAYER_JOINED") {
            val lobby = _activeLobby.value!!
            val user = payload as User
            lobby.join(user)
            _activeLobby.postValue(lobby)
        }
        if (event == "ALL_USERS_SUBMITTED") {
            viewModelScope.launch {
                repository.updateCurrentRound(game.value!!.id)
            }
        }

        if (event == "GAME_CREATED" && _game.value == null) {
            viewModelScope.launch {
                val id = payload as String
                getGame(id)
            }
        }
    }

    fun createGame() {
        viewModelScope.launch {
            val gameId = repository.createGame(activeLobby.value!!, 5)
            getGame(gameId)
        }
    }

    private suspend fun getGame(id: String) {
        val game = repository.getGame(id)
        _game.postValue(game)
        repository.listenToAnswers(game)
    }

    fun submitWord(word: String) {
        val game = this.game.value ?: return
        val round = game.rounds[game.current_round - 1]
        viewModelScope.launch {
            if (round.isValidWord(word)) {
                repository.updateAnswerToUser(game, user.value!!, word)
            }
        }
    }
}