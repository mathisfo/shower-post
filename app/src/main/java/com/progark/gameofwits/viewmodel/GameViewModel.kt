package com.progark.gameofwits.viewmodel


import androidx.lifecycle.*
import com.progark.gameofwits.model.Letters
import com.progark.gameofwits.model.Words
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.model.Player
import com.progark.gameofwits.storage.documents.GameDoc
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

    fun getGame(id: String) = liveData {
        emit(repository.getGame(id))
    }

    fun createLetterArray(): List<String> = List(10) {
        ('A'..'Z').random().toString()
    }

    fun createGame(lobbyID: String, numberOfTurns: Int, players: List<Player>) = liveData {
        val letters = hashMapOf(
            "turn1" to createLetterArray()
        )
        var wordList = mutableMapOf<String, String>()
        players.forEach { player -> wordList[player.id] = "" }
        val wordsMap = hashMapOf(
            "turn1" to wordList.toMap()
        )
        for (i in 2..numberOfTurns) {
            letters["turn"+i] = createLetterArray()
            wordsMap["turn"+i] = wordList.toMap()
        }
        val letterArrays = Letters(letters["turn1"]!!, letters["turn2"], letters["turn3"], letters["turn4"], letters["turn5"])
        val words = Words(wordsMap["turn1"]!!, wordsMap["turn2"], wordsMap["turn3"], wordsMap["turn4"], wordsMap["turn5"])
        emit(repository.addGameToFirebase(GameDoc(null, lobbyID, 1, numberOfTurns, letterArrays, words)))
    }

    fun enterWord(userID: String, word: String, turn: Int, gameID: String) = liveData {
        emit(repository.addWordToFirebase(userID, word, turn, gameID))
    }

}