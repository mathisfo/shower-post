package storage

import com.progark.gameofwits.model.Game
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.storage.documents.GameDoc

interface Repository {

    // Firestore
    suspend fun getUserId(): String
    suspend fun getLobbies(): List<Lobby>
    suspend fun getLobby(id: String): Lobby
    suspend fun createUser(): String
    suspend fun getGame(id: String): Game
    suspend fun addGameToFirebase(game: GameDoc): String?
    suspend fun addWordToFirebase(userID: String, word: String, turn: Int, gameID: String)
    suspend fun createLobby(lobby: Lobby, hostId: String): String;
    suspend fun openLobby(lobbyId: String, pin: String)
    suspend fun joinLobbyWithPin(userId: String, username: String, lobbyPIN: String): String
    suspend fun createGame(lobby:Lobby, rounds: Int): String
    suspend fun updateAnswerToUser(game: Game, userId: String, word: String)
    suspend fun updateCurrentRound(id: String)

    // Realtime
    fun listenToLobby(lobbyId: String)
    fun listenToAnswers(game: Game)
}
