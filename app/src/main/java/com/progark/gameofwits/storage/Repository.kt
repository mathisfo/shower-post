package storage

import com.google.firebase.firestore.DocumentReference
import com.progark.gameofwits.model.Game
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.storage.documents.GameDoc
import model.User

interface Repository {
    fun getUser(): String

    fun addLobbyToFirestore(lobby: HashMap<String, Any>)
    suspend fun getLobbyID(): String
    // fun getActiveLobbies(): List<Lobby>
    // fun getLobbyById(id: String): Lobby
    suspend fun getLobbies(): List<Lobby>
    suspend fun getLobby(id: String): Lobby
    suspend fun createUser(name: String)
    suspend fun getGame(id: String): GameDoc
    suspend fun addGameToFirebase(game: GameDoc): String?
    suspend fun addWordToFirebase(userID: String, word: String, turn: Int, gameID: String)

}
