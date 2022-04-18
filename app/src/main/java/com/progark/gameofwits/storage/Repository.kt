package storage

import com.google.firebase.firestore.DocumentReference
import com.progark.gameofwits.model.Game
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.storage.documents.GameDoc
import model.User

interface Repository {

    // Firestore
    suspend fun getUserId(): String
    suspend fun getLobbies(): List<Lobby>
    suspend fun getLobby(id: String): Lobby
    suspend fun createUser(name: String): String
    suspend fun getGame(id: String): GameDoc
    suspend fun addGameToFirebase(game: GameDoc): String?
    suspend fun addWordToFirebase(userID: String, word: String, turn: Int, gameID: String)
    suspend fun createLobby(lobby: Lobby, hostId: String): String;
    suspend fun openLobby(lobbyId: String, pin: String)
    suspend fun joinLobbyWithPin(userId: String, username: String, lobbyPIN: String): String

    // Realtime
    fun listenToLobby(lobbyId: String)
}
