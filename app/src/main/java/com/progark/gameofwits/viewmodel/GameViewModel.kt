package com.progark.gameofwits.viewmodel


import androidx.lifecycle.*
import com.progark.gameofwits.model.*
import com.progark.gameofwits.observers.PlayerObserver
import kotlinx.coroutines.launch
import model.User
import storage.Repository
import storage.Storage
import java.util.*

class GameViewModel(private val repository: Repository = Storage.getInstance()) : ViewModel(),
    PlayerObserver {
    private val _activeLobby = MutableLiveData<Lobby>()
    val activeLobby: LiveData<Lobby> = _activeLobby

    private val _submittedWords = MutableLiveData<Int>(0)
    val submittedWords: LiveData<Int> = _submittedWords

    private val _game = MutableLiveData<Game>()
    val game: LiveData<Game> = _game

    private val _user = MutableLiveData<String>()
    val user: LiveData<String> = _user

    private val _words = MutableLiveData<List<String>>()

    private val _validOrError = MutableLiveData<Pair<Boolean, String>>()
    val validOrError: LiveData<Pair<Boolean, String>> = _validOrError

    init {
        viewModelScope.launch {
            val words = repository.loadValidWords()
            _words.postValue(words)
        }
    }

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

    fun goToNextRound() {
        var game = _game.value!!
        println("GVM next round: " + game.current_round)
        game.current_round += 1
        _game.postValue(game)
    }

    fun updateCurrentRound() {
        if(game.value!!.current_round < game.value!!.max_round) {
            viewModelScope.launch {
                repository.updateCurrentRound(game.value!!.id)
            }
        }
    }

    override fun update(event: String, payload: Any?) {
        if (event == "PLAYER_JOINED") {
            val lobby = _activeLobby.value!!
            val user = payload as User
            lobby.join(user)
            _activeLobby.postValue(lobby)
        }
        else if (event == "USER_SUBMITTED") {
            _submittedWords.postValue(payload as Int)
        } else if (event == "GAME_CREATED" && _game.value == null) {
            viewModelScope.launch {
                val id = payload as String
                getGame(id)
            }
        }
        else if (event == "NEXT_ROUND") {
            _submittedWords.postValue(0)
            viewModelScope.launch {
                getGame(_game.value!!.id)
            }
            goToNextRound()
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
        repository.listenToNextRound(game)
    }

    fun submitWord(word: String) {
        val game = this.game.value ?: return
        val round = game.rounds[game.current_round - 1]
        viewModelScope.launch {
            val words = getWords()

            if (!round.isValidLetters(word)) {
                _validOrError.postValue(Pair(false, "Word does not contain the provided letters"))
            } else if (!words.contains(word.lowercase(Locale.getDefault()))) {
                _validOrError.postValue(Pair(false, "$word is not a valid word"))
            } else {
                repository.updateAnswerToUser(game, user.value!!, word)
                _validOrError.postValue(Pair(true, ""))
            }
        }
    }

    private suspend fun getWords(): List<String> {
        val words = _words.value
        if (words == null) {
            val data = repository.loadValidWords()
            _words.postValue(data)
            return data
        }
        return words
    }
}