package storage

import com.google.firebase.firestore.DocumentReference
import com.progark.gameofwits.model.Game
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.storage.documents.GameDoc
import model.User

interface Repository {
    fun getUser(): String

    suspend fun getLobbyID(): String
    // fun getActiveLobbies(): List<Lobby>
    // fun getLobbyById(id: String): Lobby
    suspend fun getLobbies(): List<Lobby>
    suspend fun getLobby(id: String): Lobby
    suspend fun createUser(name: String)
    suspend fun getGame(id: String): GameDoc
    suspend fun addGameToFirebase(game: GameDoc): String?
    suspend fun addWordToFirebase(userID: String, word: String, turn: Int, gameID: String)
    suspend fun createLobbyAndAddToStore(lobby: Lobby): String;
    suspend fun getLobbyByPIN(PIN: String): Lobby;

}
