package com.progark.gameofwits.viewmodel


import androidx.lifecycle.*
import com.google.firebase.firestore.DocumentReference
import com.progark.gameofwits.model.Letters
import com.progark.gameofwits.model.Lobby
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

    fun createGame(lobbyID: String, numberOfTurns: Int) = liveData {
        val letters = hashMapOf(
            "turn1" to createLetterArray()
        )
        for (i in 2..numberOfTurns) {
            letters["turn"+i] = createLetterArray()
        }
        val letterArrays = Letters(letters["turn1"]!!, letters["turn2"], letters["turn3"], letters["turn4"], letters["turn5"])
        var doc: String? = null
            emit(repository.addGameToFirestore(GameDoc(null, lobbyID, 1, numberOfTurns, letterArrays)))
    }

}